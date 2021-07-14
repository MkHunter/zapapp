import { Paper, Typography } from '@material-ui/core';
import React from 'react'
import { BarChart, 
         CartesianGrid, 
         XAxis, 
         Legend, 
         Bar } from 'recharts';

function CustomBarChart(props: { dPedidosList : any[]}) {
    const { dPedidosList } = props;
    return (
        <Paper>
            <Typography variant="h6">
                Relación Pedidos
            </Typography>
            <BarChart width={600} height={250} data={dPedidosList}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="folioPedido" />
                    <XAxis />
                    <Legend />
                    <Bar dataKey="monto" fill="#91705f" />
                    <Bar dataKey="monto" fill="#c8ceca" />
            </BarChart>
        </Paper>
    )
}
export default CustomBarChart;