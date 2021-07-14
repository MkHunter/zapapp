import React, { useEffect, useState } from 'react';
import {IconButton, Table, 
        TableBody, 
        TableCell, 
        TableContainer, 
        TableHead, 
        TablePagination, 
        TableRow } from '@material-ui/core';

import { Paper } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';
import { Info as DetalleIcon,
         LibraryAddCheck as EstadoIcon } from "@material-ui/icons";
import EditarEstado from "./EditarEstado";
import DetallePedido from "./Detalle";
import CrearPedido from "./Crear";
import { connect } from "react-redux";
import * as actionsPedidos from "../../../actions/dPedidos";
import * as actionsClientes from "../../../actions/dClientes";
import { PedidosType, ClientesType, CreatePedidosType } from "../../../helpers/AppTypes";

interface Column {
  id: 'folioPedido' | 'estadoPedido' | 'cliente' | 'fechaPedido' | 'fechaEstimada' | 'abonado' | 'monto';
  label: string;
  minWidth?: number;
  align?: 'right';
  format?: ((value: number) => string) | ((value: string) => string);
}

const columns: Column[] = [
  { id: 'folioPedido', label: 'Folio', minWidth: 50, align: 'right' },
  { id: 'estadoPedido', label: 'Estado', minWidth: 100, align: 'right' },
  { id: 'cliente', label: 'Cliente', minWidth: 100, align: 'right' },
  { id: 'fechaPedido', label: 'Fecha Pedido', minWidth: 100, align: 'right'  },
  { id: 'fechaEstimada', label: 'Fecha Estimada', minWidth: 10, align: 'right' },
  { id: 'abonado', label: 'Abonado($)', align: 'right', minWidth: 100 },
  { id: 'monto', label: 'Monto($)', align: 'right', minWidth: 100 }
];

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 550,
  },
});

const descEstado : string[] = ["","En Revisión", "En Reparación", "Atrasado", "Terminado"]

interface PedidosProps{
  dPedidosList: PedidosType[],
  dClientesList : ClientesType[],
  dPedido : CreatePedidosType,
  fetchAllDPedidos : any,
  fetchIdDPedido : any,
  fetchAllDClientes : any,
  dUsuarioToken : string
}

function Pedidos(props: PedidosProps) {
  const { dPedidosList, fetchAllDPedidos, fetchIdDPedido, dPedido, dClientesList, fetchAllDClientes, dUsuarioToken } = props;
  const classes = useStyles();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [dialogEstadoOpen, setEstadoDialogOpen] = useState(false);
  const [dialogDetalleOpen, setDetalleDialogOpen] = useState(false);
  const [folioPed, setFolioPed] = useState<number>(0);
  const [pedido, setPedido] = useState<PedidosType>({
    usuario: 0,
    folioPedido: 0,
    descripcion: "",
    fechaPedido: "",
    fechaEstimada: "",
    cliente: 0,
    estadoPedido: 0,
    abonado: 0,
    monto: 0,
  });

  const handleEstadoDialogOpen = (folioPedido : number) => {
    setPedido(dPedidosList.find(ped => ped.folioPedido === folioPedido) as PedidosType);
    setEstadoDialogOpen(true);
  };
  
  const handleDetalleDialogOpen = (folioPedido : number) => {
    //setPedido(dPedidosList.find(ped => ped.folioPedido === folioPedido) as PedidosType);
    fetchIdDPedido(folioPedido, dUsuarioToken);
    setDetalleDialogOpen(true);
  };

  const handleDetalleDialogClose = () => {
    setDetalleDialogOpen(false);
  };

  const handleEstadoDialogClose = () => {
    setEstadoDialogOpen(false);
  };

  useEffect(() =>{
    fetchAllDPedidos(dUsuarioToken);
    fetchAllDClientes(dUsuarioToken);
  },[]);

  /*
  useEffect(() =>{
    //setFolioPed(pedido.folioPedido);
    
  },[pedido]);
  */
  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  function parseData(row : any,columnid : string) : string {
    switch (columnid) {
      case "cliente":
        const n : number = row[columnid];
        const cliente : (ClientesType | undefined) = dClientesList.find(cliente => cliente.identificador === n);
        return (cliente? cliente.nombre: "Cliente Inexistente");
      case "estadoPedido":
        return descEstado[row[columnid]]
      default:
        return row[columnid]
    }
  }

  return (
    <Paper className={classes.root}>
      <EditarEstado pedido={pedido} dUsuarioToken={dUsuarioToken} handleDialogClose={handleEstadoDialogClose} open={dialogEstadoOpen}/>
      { dPedido && <DetallePedido dPedido={dPedido} handleDialogClose={handleDetalleDialogClose} open={dialogDetalleOpen}/>}
      <CrearPedido dUsuarioToken={dUsuarioToken}/>
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
              <TableCell
                  key="acciones"
                  align="right"
                  style={{ minWidth: 100 }}
              >Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {dPedidosList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row : PedidosType) => {
              return (
                <TableRow key={row.folioPedido} hover role="checkbox" tabIndex={-1}>
                  {columns.map((column) => {
                    return (
                      <TableCell key={column.id} align={column.align}>
                        {
                          parseData(row, column.id)
                        }
                      </TableCell>
                    );
                  })}
                  <TableCell align="right" >
                      <IconButton color="primary" aria-label="detail" onClick={()=> handleDetalleDialogOpen(row.folioPedido)}>
                        <DetalleIcon/>
                      </IconButton>
                      <IconButton color="primary" aria-label="update" onClick={()=> handleEstadoDialogOpen(row.folioPedido)}>
                        <EstadoIcon/>
                      </IconButton>
                    </TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={dPedidosList.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />
    </Paper>
  );
}
const mapStateToProps = (state : any) => ({
  dPedidosList: state.dPedidos.listPedidos,
  dPedido: state.dPedidos.checkPedido,
  dClientesList: state.dClientes.listClientes,
  dUsuarioToken: state.dUsuario.token
})

const mapActionToProps = {
  fetchAllDPedidos: actionsPedidos.fetchAll,
  fetchIdDPedido: actionsPedidos.fetchById,
  fetchAllDClientes: actionsClientes.fetchAll
}

export default connect(mapStateToProps, mapActionToProps)(Pedidos);
