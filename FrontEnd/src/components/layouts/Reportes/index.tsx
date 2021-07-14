import React, { Fragment, useEffect } from 'react';
import { PedidosType, 
         ServicioType, 
         ClientesType, 
         ServiciosType,
         ReporteType } from "../../../helpers/AppTypes";

import CustomPieChart from "./CustomPieChart";
//import CustomBarChart from "./CustomBarChart";
import TopAbonosClientes from "./TopAbonosClientes";

import * as actionsPedidos from "../../../actions/dPedidos";
import * as actionsClientes from "../../../actions/dClientes";
import * as actionsServicios from "../../../actions/dServicios";
import * as actionsReportes from "../../../actions/dReportes";
import { connect } from 'react-redux';
import { Grid } from '@material-ui/core';

interface ReportesProps{
    dPedidosList: PedidosType[],
    dClientesList: ClientesType[],
    dServiciosList: ServiciosType[],
    dReportesList: ReporteType[], 
    dUsuarioToken: any,
    fetchAllDPedidos: any,
    fetchAllDClientes: any,
    fetchAllDServicios: any,
    fetchAllDReportes : any
}

function Reportes(props: ReportesProps) {
    const { dPedidosList,
            dClientesList,
            dServiciosList,
            dReportesList,
            dUsuarioToken,
            fetchAllDPedidos,
            fetchAllDClientes,
            fetchAllDServicios,
            fetchAllDReportes } = props;

    useEffect(() =>{
        fetchAllDPedidos(dUsuarioToken);
        fetchAllDClientes(dUsuarioToken);
        fetchAllDServicios(dUsuarioToken);
        fetchAllDReportes(dUsuarioToken);
    },[]);
    return (
        <Fragment>
            <Grid
            container
            direction="row"
            justify="space-around"
            alignItems="center"
            >
            <Grid item>
                <CustomPieChart dReportesList={dReportesList}/>
            </Grid>
            <Grid item>
                <TopAbonosClientes dPedidosList={dPedidosList} dClientesList={dClientesList}/>
            </Grid>
            </Grid>
            {
                /**
            <Grid
            container
            direction="row"
            justify="flex-start"
            alignItems="center"
            >
                <CustomBarChart dPedidosList={dPedidosList}/>
            </Grid>
                */
            }
        </Fragment>
    )
}

const mapStateToProps = (state : any) => ({
    dPedidosList: state.dPedidos.listPedidos,
    dClientesList: state.dClientes.listClientes,
    dServiciosList: state.dClientes.listServicios,
    dReportesList: state.dReportes.listReportes,
    dUsuarioToken: state.dUsuario.token
  })
  
  const mapActionToProps = {
    fetchAllDPedidos: actionsPedidos.fetchAll,
    fetchAllDClientes: actionsClientes.fetchAll,
    fetchAllDServicios: actionsServicios.fetchAll,
    fetchAllDReportes: actionsReportes.fetchAll
  }
  
  export default connect(mapStateToProps, mapActionToProps)(Reportes);
  