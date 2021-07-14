import React, { useEffect, useState } from 'react';
import {IconButton, 
        Table, 
        TableBody, 
        TableCell, 
        TableContainer, 
        TableHead, 
        TablePagination, 
        TableRow } from '@material-ui/core';
import { AddComment as CommentIcon } from '@material-ui/icons';
import { Paper } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';

import EditarConversacion from "./Editar";
import { connect } from "react-redux";
import * as actionsConversaciones from "../../../actions/dConversaciones";
import * as actionsClientes from "../../../actions/dClientes";
import { ConversacionesType, ClientesType } from "../../../helpers/AppTypes";

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 550,
  },
});

interface Column {
  id: 'idHilo' | 'usuarioPost' |  'fechaPost' | 'estado' | 'postPrincipal';
  label: string;
  format?: (value: number) => string;
}

const columns: Column[] = [
  { id: 'idHilo', label: 'ID' },
  { id: 'usuarioPost', label: 'Usuario' },
  { id: 'fechaPost', label: 'Fecha' },
  { id: 'postPrincipal', label: 'Título de Post'}
];

interface ConversacionesProps{ 
  dConversacionesList : ConversacionesType[], 
  fetchAllDConversaciones : any,
  dUsuarioToken : string,
  dClientesList : ClientesType[],
  fetchAllDClientes : any,
}
function Conversaciones(props : ConversacionesProps) {
  const classes = useStyles();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const { dConversacionesList, fetchAllDConversaciones, dUsuarioToken, dClientesList, fetchAllDClientes} = props;
  const [dialogOpen, setDialogOpen] = useState(false);
  const [idHilo, setIdHilo] = useState<number>(0);

  const handleDialogOpen = (idHilo : number) => {
    setIdHilo(idHilo);
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
    fetchAllDConversaciones(dUsuarioToken);
    fetchAllDClientes(dUsuarioToken);
  }, []);

  function parseData(row : any, columnid : string) : string {
    switch (columnid) {
      case "usuarioPost":
        const n : number = row[columnid];
        const cliente : (ClientesType | undefined) = dClientesList.find(cliente => cliente.identificador === n);
        return (cliente? cliente.nombre: "Cliente Inexistente");
      default:
        return row[columnid]
    }
  }

  return (
    <Paper className={classes.root}>
      <EditarConversacion idHilo={idHilo} dClientesList={dClientesList} dUsuarioToken={dUsuarioToken} handleDialogClose={handleDialogClose} open={dialogOpen}/>
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
                  key="conacciones"
                  align="right"
                  style={{ minWidth: 170 }}
              >Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {dConversacionesList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((conversacion) => {
              return (
                <TableRow key={conversacion.idHilo} hover role="checkbox" tabIndex={-1}>
                  {columns.map((column) => {
                    return (
                      <TableCell key={column.id} align="right">
                        {parseData(conversacion, column.id)}
                      </TableCell>
                    );
                  })}
                  <TableCell key="conacciones" align="right" >
                    {
                        <IconButton color="primary" aria-label="edit" onClick={() => handleDialogOpen(conversacion.idHilo)}>
                        <CommentIcon/>
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
        count={dConversacionesList.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />
    </Paper>
  );
}
const mapStateToProps = (state : any) => ({
  dConversacionesList: state.dConversaciones.listConversaciones,
  dClientesList: state.dClientes.listClientes,
  dUsuarioToken: state.dUsuario.token
})

const mapActionToProps = {
  fetchAllDConversaciones: actionsConversaciones.fetchAll,
  fetchAllDClientes: actionsClientes.fetchAll
}

export default connect(mapStateToProps,mapActionToProps)(Conversaciones);