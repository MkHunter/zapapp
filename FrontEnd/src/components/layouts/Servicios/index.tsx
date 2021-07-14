import React, { useEffect, useState } from 'react';
import {Table, 
        TableBody, 
        TableCell, 
        TableContainer, 
        TableHead, 
        TablePagination, 
        TableRow } from '@material-ui/core';

import { Paper } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';

import CrearServicio from "./Crear";
import { connect } from "react-redux";
import * as actions from "../../../actions/dServicios";
import { ServiciosType } from "../../../helpers/AppTypes";

interface Column {
  id: 'folioServicio' | 'nombreServicio' |'rangoMin' | 'max';
  label: string;
  minWidth?: number;
  align?: 'right';
  format?: ((value: number) => string) | ((value: string) => string);
}

const columns: Column[] = [
  { id: 'folioServicio', label: 'Folio', align: 'right',  minWidth: 50 },
  { id: 'nombreServicio', label: 'Nombre/s', align: 'right', minWidth: 100 },
  { id: 'rangoMin', label: 'Min($)', align: 'right', minWidth: 100 },
  { id: 'max', label: 'Max($)', align: 'right', minWidth: 100 }
];

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 550,
  },
});

function Servicios(props: {dServiciosList: ServiciosType[],  fetchAllDServicios : any, dUsuarioToken : string}) {
  const { dServiciosList, fetchAllDServicios, dUsuarioToken } = props;
  const classes = useStyles();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  
  useEffect(() =>{
    fetchAllDServicios(dUsuarioToken);
  },[]);
  
  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  
  return (
    <Paper className={classes.root}>
      <CrearServicio dUsuarioToken={dUsuarioToken}/>
      <TableContainer className={classes.container}>
        <Table>
          <TableHead>
            <TableRow>
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {dServiciosList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row : ServiciosType) => {
              return (
                <TableRow key={row.folioServicio} hover role="checkbox" tabIndex={-1}>
                  <TableCell align={'right'} style={{ minWidth: 50}}>{row.folioServicio}</TableCell>
                  <TableCell align={'right'} style={{ minWidth: 100}}>{row.nombreServicio}</TableCell>
                  <TableCell align={'right'} style={{ minWidth: 100}}>{row.rangoMin}</TableCell>
                  <TableCell align={'right'} style={{ minWidth: 100}}>{row.rangoMax}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={dServiciosList.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />
    </Paper>
  );
}
const mapStateToProps = (state : any) => ({
  dServiciosList: state.dServicios.listServicios,
  dUsuarioToken: state.dUsuario.token
})

const mapActionToProps = {
  fetchAllDServicios: actions.fetchAll
  //deleteDServicio: actions.Delete
}

export default connect(mapStateToProps, mapActionToProps)(Servicios);