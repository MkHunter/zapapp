import { Paper, Typography } from '@material-ui/core';
import React from 'react'
import { Cell, Legend, Pie, PieChart, ResponsiveContainer } from 'recharts';
import { ReporteType } from "../../../helpers/AppTypes";

function CustomPieChart(props: { dReportesList : ReporteType[]}) {
    const { dReportesList } = props;
    const ranges : string[] = ['#a1887f','#8d6e63','#795548','#6d4c41','#5d4037','#4e342e','#3e2723'];
    return (
      <Paper>
        <Typography variant="h6">
          Ganancias por servicio
        </Typography>
        <ResponsiveContainer width={600} height={250}>
        <PieChart>
            <Legend />
            <Pie data={dReportesList} dataKey="costo" nameKey="nombre" cx="50%" cy="50%" outerRadius={80} label isAnimationActive={false}>
              {
                dReportesList.map((range : ReporteType,index : number) => <Cell key={range.folio} fill={ranges[index % ranges.length] }/>)
              }
            </Pie>
        </PieChart>
        </ResponsiveContainer>
    </Paper>
    )
}

export default CustomPieChart;