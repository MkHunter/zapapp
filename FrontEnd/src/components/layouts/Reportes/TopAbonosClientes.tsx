import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@material-ui/core';
import React, { useEffect, useState, Fragment } from 'react'
import { PedidosType, 
         ClientesType } from "../../../helpers/AppTypes";

interface TopCliente{
    cliente: number;
    abonoTotal: number;
}

function TopAbonosClientes(props: {dClientesList : ClientesType[], dPedidosList : PedidosType[]}) {
    const { dPedidosList, dClientesList } = props;
    const [info, setInfo] = useState<{cliente: number, abonoTotal: number}[]>();
    //getAbonoPedidosClientes(clientesIDs,dPedidosList);
    useEffect(() =>{
        if( dPedidosList ){
            const clientesIDs : number[] = dClientesList.map(cliente => cliente.identificador);
            setInfo(getAbonoPedidosClientes(clientesIDs,dPedidosList));
        }
    },[]);

    function getAbonoPedidosClientes( clienteId : (number[] | undefined), pedidos : PedidosType[]) : TopCliente[]{
        if(!clienteId){
            return [];
        }
        let abonoClientes: TopCliente[] = [];
        for (let cli = 0; cli < clienteId.length; cli++) {
            let cliente = clienteId[cli];
            let sumAbono : number = 0;
            for (let index = 0; index < pedidos.length; index++) {
                if(cliente === pedidos[index]['cliente']){
                    sumAbono += pedidos[index]['abonado'];
                }
            }
            abonoClientes.push({cliente: clienteId[cli],abonoTotal: sumAbono});
        }
        abonoClientes.sort(function(a,b){
            return a.abonoTotal - b.abonoTotal;
            }
        );
        return abonoClientes.reverse().slice(0,10);
    }
    function getUserName(idUsuario : number) : string {
        const cliente : (ClientesType | undefined) = dClientesList.find(cliente => cliente.identificador === idUsuario);
        return (cliente? cliente.nombre: "Yo");
    }
    return (
        <Paper>
            <Typography variant="h6">
                Top 10 Clientes
            </Typography>
            {
                info &&
                <TableContainer component={Paper}>
                <Table size="small">
                    <TableHead>
                    <TableRow>
                        <TableCell align="right">Cliente</TableCell>
                        <TableCell align="right">Abono total</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {info.map((topcli : TopCliente) => (
                        <TableRow key={topcli.cliente}>
                        <TableCell align="right">{getUserName(topcli.cliente)}</TableCell>
                        <TableCell align="right">{topcli.abonoTotal}</TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
                </TableContainer>
            }
        </Paper>
    )
}

export default TopAbonosClientes;