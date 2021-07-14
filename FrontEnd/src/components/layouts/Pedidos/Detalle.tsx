import React, { Fragment, useEffect, useState } from 'react';
import { Dialog,
         DialogActions,
         DialogContent,
         DialogContentText,
         DialogTitle, 
         Typography} from "@material-ui/core";
import { Box, Button, Grid } from "@material-ui/core";

import { connect } from "react-redux";
import * as actions from "../../../actions/dPedidos";

import Chip from '@material-ui/core/Chip';

import { CreatePedidosType, ServicioType } from "../../../helpers/AppTypes";

interface DetallePedidoProps{ 
    //folioPedido : number, 
    //dUsuarioToken: string, 
    handleDialogClose : any, 
    open : boolean, 
    dPedido : CreatePedidosType//,
    //fetchIdDPedido : any
}

function DetallePedido(props : DetallePedidoProps) {
    const { dPedido, handleDialogClose, open } = props;
    
    return (
        <Fragment>
        <Dialog open={open} onClose={handleDialogClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
            <DialogTitle id="detail-dialog-title" style={{ minWidth: 400 }}>Detalle del Pedido</DialogTitle>
            <DialogContent>
                <Grid
                container
                direction="column"
                justify="space-evenly"
                alignItems="flex-start"
                >
                <DialogContentText>
                    Servicios
                </DialogContentText>
                <Grid>
                { dPedido.servicios && dPedido.servicios.map(function showServicios(servicio: ServicioType){
                    return(
                        <Chip key={servicio.folio} label={servicio.nombre+": $"+servicio.costo} color="primary"/>
                    );
                })}
                </Grid>
                <DialogContentText>
                    Descripción
                </DialogContentText>
                <Grid>
                {dPedido.descripcion?<Typography>{dPedido.descripcion}</Typography>: <Typography color='error'>No adjuntada</Typography>}
                </Grid>
                </Grid>
            </DialogContent>
            <DialogActions>
                <Box>
                    <Button onClick={handleDialogClose} color="primary">
                        Cerrar
                    </Button>
                </Box>
            </DialogActions>
        </Dialog>
        </Fragment>
    );
}
 
  const mapStateToProps = (state : any, props : { dPedido : CreatePedidosType, handleDialogClose : any, open : boolean}) => ({
    ...props
  })
  
  const mapActionToProps = {
    fetchIdDPedido: actions.fetchById
  }

export default connect(mapStateToProps, {})(DetallePedido);