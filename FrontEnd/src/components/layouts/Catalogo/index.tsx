import React, { useEffect, useState } from 'react';
import {IconButton, 
        Table, 
        TableBody, 
        TableCell, 
        TableContainer, 
        TableHead, 
        TablePagination, 
        TableRow } from '@material-ui/core';
import { Image as ImageIcon } from '@material-ui/icons';
import { Paper } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';

import EditarCliente from "./Editar";
import CrearCliente from "./Crear";
import { connect } from "react-redux";
import * as actions from "../../../actions/dCatalogo";
import { CatalogoType } from "../../../helpers/AppTypes";

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 550,
  },
});

interface Column {
  id: 'idProducto' | 'marca' |  'color' | 'precio' | 'talla' | 'imagen';
  label: string;
  format?: (value: number) => string;
}

const columns: Column[] = [
  { id: 'idProducto', label: 'ID' },
  { id: 'marca', label: 'Marca' },
  { id: 'color', label: 'Color' },
  { id: 'precio', label: 'Precio' },
  { id: 'talla', label: 'Talla'}
];

function Catalogo(props : { dCatalogoList : CatalogoType[], fetchAllDCatalogo : any, dUsuarioToken : string}) {
  const classes = useStyles();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const { dCatalogoList, fetchAllDCatalogo, dUsuarioToken} = props;
  const [dialogOpen, setDialogOpen] = useState(false);
  const [catalogo, setCatalogo] = useState<CatalogoType>({
    idProducto: 0,
    marca: "",
    color: "",
    imagen: "",
    precio: 0,
    talla: 0
  });

  const handleDialogOpen = (idProducto : number) => {
    setCatalogo(dCatalogoList.find(cat => cat.idProducto === idProducto) as CatalogoType);
    setDialogOpen(true);
  };

  const handleDialogClose = () => {
    setDialogOpen(false);
  };

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  useEffect(() =>{
    fetchAllDCatalogo(dUsuarioToken);
  }, []);

  return (
    <Paper className={classes.root}>
      <CrearCliente dUsuarioToken={dUsuarioToken}/>
      <EditarCliente imagen={catalogo? catalogo.imagen as string: ""} dUsuarioToken={dUsuarioToken} handleDialogClose={handleDialogClose} open={dialogOpen}/>
      <TableContainer className={classes.container}>
        <Table>
          <TableHead>
            <TableRow>
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  align="right"
                  style={{ minWidth: 150 }}
                >
                  {column.label}
                </TableCell>
              ))}
              <TableCell
                  key="catacciones"
                  align="right"
                  style={{ minWidth: 170 }}
              >Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {dCatalogoList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((catalogo) => {
              return (
                <TableRow key={catalogo.idProducto} hover role="checkbox" tabIndex={-1}>
                  {columns.map((column) => {
                    return (
                      <TableCell key={column.id} align="right">
                        {catalogo[column.id]}
                      </TableCell>
                    );
                  })}
                  <TableCell key="catacciones" align="right" >
                    {
                        <IconButton color="primary" aria-label="edit" onClick={() => handleDialogOpen(catalogo.idProducto)}>
                        <ImageIcon/>
                        </IconButton>
                    }
                    </TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[5,10, 25, 100]}
        component="div"
        count={dCatalogoList.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />
    </Paper>
  );
}
const mapStateToProps = (state : any) => ({
  dCatalogoList: state.dCatalogo.listCatalogo,
  dUsuarioToken: state.dUsuario.token
})

const mapActionToProps = {
  fetchAllDCatalogo: actions.fetchAll
}

export default connect(mapStateToProps,mapActionToProps)(Catalogo);