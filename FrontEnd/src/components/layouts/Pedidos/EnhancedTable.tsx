import React, { useState, useEffect } from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Checkbox from '@material-ui/core/Checkbox';

import InputSlider from "./InputSlider";
import * as actionsServicios from "../../../actions/dServicios";
import { connect } from 'react-redux';
import { ServiciosType, ServicioType } from "../../../helpers/AppTypes";

interface HeadCell {
  id: keyof ServicioType;
  label: string;
}

const headCells: HeadCell[] = [
  { id: 'folio', label: 'Folio' },
  { id: 'nombre', label: 'Nombre' },
  { id: 'costo', label: 'Costo' }
];

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      width: '100%',
    },
    paper: {
      width: '100%',
      marginBottom: theme.spacing(2),
    },
    table: {
      minWidth: 750,
    }
  }),
);

function EnhancedTable(props : { dServiciosList : any, setServicios: any, fetchAllDServicios : any}) {
  const { dServiciosList, fetchAllDServicios, setServicios} = props;
  const classes = useStyles();
  const [selected, setSelected] = useState<ServicioType[]>([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [listCostos, setListCostos] = useState<number[]>([]);

  const handleClick = (event: React.MouseEvent<unknown>, servicio: ServicioType, index: number) => {
    const selectedIndex = selected.map(serv => serv.folio).indexOf(servicio.folio);
    let newSelected: ServicioType[] = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, {...servicio, costo: listCostos[index]} as ServicioType);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1),
      );
    }
    setSelected(newSelected);
    setServicios(newSelected);
  };

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const isSelected = (folio: number) : boolean => selected.map(serv => serv.folio).indexOf(folio) !== -1;

  const emptyRows = rowsPerPage - Math.min(rowsPerPage, dServiciosList.length - page * rowsPerPage);
  
  useEffect(()=>{
    fetchAllDServicios();
  },[]);

  useEffect(()=>{
    const listaCostos : number[] = dServiciosList.map(function getCostos(servicio: ServiciosType){
      const costo : number = (servicio.rangoMin+servicio.rangoMax) / 2;
      return(
        costo
      );
    })
    setListCostos(listaCostos);
  },[dServiciosList])

  //Toolbar typografy className = classes.title
  return (
    <div className={classes.root}>
      <Paper className={classes.paper}>
        <Toolbar>
          <Typography variant="h6" id="tableTitle" component="div">
              Servicios
          </Typography>
        </Toolbar>
        <TableContainer>
          <Table
            className={classes.table}
            aria-labelledby="tableTitle"
            size={'small'}
            aria-label="enhanced table"
          >
             <TableHead>
              <TableRow>
                <TableCell padding="checkbox">
                </TableCell>
                {headCells.map((headCell) => (
                  <TableCell
                    key={headCell.id}
                    align={'right'}
                    padding={'default'}
                  >
                  {headCell.label}
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody>
              {listCostos.length > 0 && dServiciosList
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row : ServiciosType, index : number) => {
                  const costo : number = listCostos[index];
                  const serv : ServicioType = {folio: row.folioServicio, nombre: row.nombreServicio, costo: 0};
                  const isItemSelected = isSelected(row.folioServicio);
                  const labelId = `enhanced-table-checkbox-${index}`;
                  return (
                    <TableRow
                      hover
                      onClick={(event) => handleClick(event, serv, index)}
                      role="checkbox"
                      aria-checked={isItemSelected}
                      tabIndex={-1}
                      key={row.folioServicio}
                      selected={isItemSelected}
                    >
                      <TableCell padding="checkbox" align={'right'}>
                        <Checkbox
                          checked={isItemSelected}
                          inputProps={{ 'aria-labelledby': labelId }}
                        />
                      </TableCell>
                      <TableCell component="th" id={labelId} scope="row" padding="none" align={'right'}>
                        {row.folioServicio}
                      </TableCell>
                      <TableCell align="right">{row.nombreServicio}</TableCell>
                      <TableCell align="right">
                        <InputSlider isSelected={isItemSelected} min={row.rangoMin} max={row.rangoMax} initialValue={costo} setListCostos={setListCostos} costoIndex={index} listCostos={listCostos}/>
                      </TableCell>
                    </TableRow>
                  );
                })}
              {emptyRows > 0 && (
                <TableRow style={{ height: 33 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={dServiciosList.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onChangePage={handleChangePage}
          onChangeRowsPerPage={handleChangeRowsPerPage}
        />
      </Paper>
    </div>
  );
}

const mapStateToProps = (state : any, props: { setServicios: any }) => ({
  ...props,
  dServiciosList: state.dServicios.listServicios
});

const mapActionToProps = {
  fetchAllDServicios: actionsServicios.fetchAll
}
export default connect(mapStateToProps,mapActionToProps)(EnhancedTable);
