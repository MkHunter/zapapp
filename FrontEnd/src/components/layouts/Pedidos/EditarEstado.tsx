import React, { Fragment } from 'react';
import { Dialog,
         DialogActions,
         DialogContent,
         DialogContentText,
         DialogTitle } from "@material-ui/core";
import { Box, Button, Grid } from "@material-ui/core";

import { connect } from "react-redux";
import * as actions from "../../../actions/dPedidos";

import { makeStyles } from '@material-ui/core/styles';
import Timeline from '@material-ui/lab/Timeline';
import TimelineItem from '@material-ui/lab/TimelineItem';
import TimelineSeparator from '@material-ui/lab/TimelineSeparator';
import TimelineConnector from '@material-ui/lab/TimelineConnector';
import TimelineContent from '@material-ui/lab/TimelineContent';
import TimelineOppositeContent from '@material-ui/lab/TimelineOppositeContent';
import TimelineDot from '@material-ui/lab/TimelineDot';

import { FindInPage as EnRevisionIcon, 
         Gavel as EnReparacionIcon, 
         Timelapse as AtrasadoIcon, 
         Check as TerminadoIcon } from '@material-ui/icons/';

import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import { PedidosType } from "../../../helpers/AppTypes";

  const useStyles = makeStyles((theme) => ({
    paper: {
      padding: '6px 16px',
    },
    secondaryTail: {
      backgroundColor: theme.palette.secondary.main,
    },
  }));

function EditarEstado(props : { pedido : PedidosType, dUsuarioToken: string, handleDialogClose : any, open : boolean, updateDPedido : any}) {
    const { pedido, dUsuarioToken, updateDPedido, handleDialogClose, open } = props;
    const classes = useStyles();

    const onClick = (incremento : number) => {
        const siguienteEstado : number = pedido.estadoPedido + incremento;
        updateDPedido(
            { folioPedido: pedido.folioPedido, estado: siguienteEstado }, 
            dUsuarioToken, 
            () => handleDialogClose()
            );
    }
    
    return (
        <Fragment>
        <Dialog open={open} onClose={handleDialogClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
            <DialogTitle id="form-dialog-title" style={{ minWidth: 300 }}>Editar estado del Pedido</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Seguimiento del estado del pedido
                </DialogContentText>
                <Grid item>
                <Timeline align="alternate">
                    <TimelineItem>
                        <TimelineOppositeContent>
                            <Typography variant="body2" color="textSecondary">
                                {pedido.fechaPedido}
                            </Typography>
                        </TimelineOppositeContent>
                        <TimelineSeparator>
                            <TimelineDot color="secondary">
                                <EnRevisionIcon />
                            </TimelineDot>
                            { pedido.estadoPedido > 1 ?
                            <TimelineConnector className={classes.secondaryTail}/>
                            :
                            <TimelineConnector/>
                            }
                        </TimelineSeparator>
                        <TimelineContent>
                            <Paper elevation={3} className={classes.paper}>
                                <Typography variant="h6" component="h1">
                                En Revisión
                                </Typography>
                                <Typography>El calzado está pasando por revisión.</Typography>
                            </Paper>
                        </TimelineContent>
                    </TimelineItem>
                    <TimelineItem>
                        <TimelineSeparator>
                            <TimelineDot color={pedido.estadoPedido > 1 ? "secondary":"grey"}>
                                <EnReparacionIcon />
                            </TimelineDot>
                        { pedido.estadoPedido > 2 ?
                        <TimelineConnector className={classes.secondaryTail}/>
                        :
                        <TimelineConnector/>
                        }
                        </TimelineSeparator>
                        <TimelineContent>
                        <Paper elevation={3} className={classes.paper}>
                            <Typography variant="h6" component="h1">
                            En Reparación
                            </Typography>
                            <Typography>En taller, aplicando servicios.</Typography>
                        </Paper>
                        </TimelineContent>
                    </TimelineItem>
                    {pedido.estadoPedido === 3  &&
                    <TimelineItem>
                        <TimelineSeparator>
                            <TimelineDot color={pedido.estadoPedido > 2 ? "secondary":"grey"}>
                                <AtrasadoIcon />
                            </TimelineDot>
                            <TimelineConnector/>
                        </TimelineSeparator>
                        <TimelineContent>
                            <Paper elevation={3} className={classes.paper}>
                                <Typography variant="h6" component="h1">
                                Atrasado
                                </Typography>
                                <Typography>Pedido con fecha pendiente.</Typography>
                            </Paper>
                        </TimelineContent>
                    </TimelineItem>
                    }
                    <TimelineItem>
                        <TimelineOppositeContent>
                            <Typography variant="body2" color="textSecondary">
                                {pedido.fechaEstimada}
                            </Typography>
                        </TimelineOppositeContent>
                        <TimelineSeparator>
                            <TimelineDot color={pedido.estadoPedido > 3 ? "secondary":"grey"}>
                                <TerminadoIcon />
                            </TimelineDot>
                        </TimelineSeparator>
                        <TimelineContent>
                            <Paper elevation={3} className={classes.paper}>
                                <Typography variant="h6" component="h1">
                                Terminado
                                </Typography>
                                <Typography>Pedido listo para ser recogido.</Typography>
                            </Paper>
                        </TimelineContent>
                    </TimelineItem>
                </Timeline>
                </Grid>
            </DialogContent>
            <DialogActions>
                <Box margin={4}>
                    <Button onClick={handleDialogClose} color="primary">
                    Cancelar
                    </Button>
                    { pedido.estadoPedido === 2 ?
                    <Fragment>
                        <Button
                        variant="contained"
                        color="primary"
                        onClick={() => {onClick(1)}}
                        >
                        Pedido Atrasado
                        </Button>
                        <Button
                        variant="contained"
                        color="primary"
                        onClick={() => {onClick(2)}}
                        >
                        Terminar Pedido
                        </Button>
                    </Fragment>
                    : 
                    <Button
                    variant="contained"
                    color="primary"
                    onClick={() => {onClick(1)}}
                    disabled={pedido.estadoPedido === 4}
                    >
                    Siguiente Estado
                    </Button>
                    }
                </Box>
            </DialogActions>
        </Dialog>
        </Fragment>
    );
}
 
  const mapStateToProps = (state : any, props : { pedido : PedidosType, dUsuarioToken: string, handleDialogClose : any, open : boolean}) => ({
    ...props
  })
  
  const mapActionToProps = {
    updateDPedido: actions.Update
  }

export default connect(mapStateToProps, mapActionToProps)(EditarEstado);