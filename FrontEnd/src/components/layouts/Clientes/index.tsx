import React, { useEffect, useState } from 'react';
import {IconButton, 
        Table, 
        TableBody, 
        TableCell, 
        TableContainer, 
        TableHead, 
        TablePagination, 
        TableRow } from '@material-ui/core';
import { Create as EditIcon } from '@material-ui/icons';
import { Paper } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';

import EditarCliente from "./Editar";
import CrearCliente from "./Crear";
import { connect } from "react-redux";
import * as actions from "../../../actions/dClientes";
import { ClientesType } from "../../../helpers/AppTypes";

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 550,
  },
});

interface Column {
  id: 'identificador' | 'nombre' |  'apellido' | 'telefono' | 'correo';
  label: string;
  format?: (value: number) => string;
}

const columns: Column[] = [
  { id: 'identificador', label: 'ID' },
  { id: 'nombre', label: 'Nombre/s' },
  { id: 'apellido', label: 'Apellido/s' },
  { id: 'telefono', label: '# Celular' },
  { id: 'correo', label: 'Correo'}
];

function Clientes(props : { dClientesList : ClientesType[], fetchAllDClientes : any, dUsuarioToken : string}) {
  const classes = useStyles();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const { dClientesList, fetchAllDClientes, dUsuarioToken} = props;
  const [dialogOpen, setDialogOpen] = useState(false);
  const [cliente, setCliente] = useState<ClientesType>({
    identificador: 0,
    nombre: "",
    apellido: "",
    telefono: "",
    correo: ""
  });

  const handleDialogOpen = (identificador : number) => {
    setCliente(dClientesList.find(cli => cli.identificador === identificador) as ClientesType);
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
    fetchAllDClientes(dUsuarioToken);
  }, []);
  
  return (
    <Paper className={classes.root}>
      <EditarCliente cliente={cliente} dUsuarioToken={dUsuarioToken} handleDialogClose={handleDialogClose} open={dialogOpen}/>
      <CrearCliente dUsuarioToken={dUsuarioToken}/>
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
                  key="cliacciones"
                  align="right"
                  style={{ minWidth: 170 }}
              >Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {dClientesList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((cliente) => {
              return (
                <TableRow key={cliente.identificador} hover role="checkbox" tabIndex={-1}>
                  {columns.map((column) => {
                    return (
                      <TableCell key={column.id} align="right">
                        {cliente[column.id]}
                      </TableCell>
                    );
                  })}
                  <TableCell key="cliacciones" align="right" >
                    {
                        <IconButton color="primary" aria-label="edit" onClick={() => handleDialogOpen(cliente.identificador)}>
                        <EditIcon/>
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
        count={dClientesList.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />
    </Paper>
  );
}
const mapStateToProps = (state : any) => ({
  dClientesList: state.dClientes.listClientes,
  dUsuarioToken: state.dUsuario.token
})

const mapActionToProps = {
  fetchAllDClientes: actions.fetchAll
}

export default connect(mapStateToProps,mapActionToProps)(Clientes);