import React, { Fragment, useEffect, useState } from 'react';
import { Dialog,
         DialogActions,
         DialogContent,
         DialogContentText,
         DialogTitle,
         Paper,
         TableContainer,
         Table,
         TableHead,
         TableRow,
         TableCell,
         TableBody} from "@material-ui/core";
import { Box, Button, Grid, TextField as MuiTextField, LinearProgress } from "@material-ui/core";
import { Formik, Form, Field } from 'formik';
import { fieldToTextField,
         TextFieldProps } from 'formik-material-ui';

import { connect } from "react-redux";
import * as actions from "../../../actions/dConversaciones";

import { ConversacionesType, Conversacion, ClientesType } from "../../../helpers/AppTypes";

function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

function MessagesTable(props: {conversaciones : Conversacion[], dClientesList: ClientesType[]}){
    const { conversaciones, dClientesList} = props;
    
    function getUserName(idUsuario : number) : string {
        const cliente : (ClientesType | undefined) = dClientesList.find(cliente => cliente.identificador === idUsuario);
        return (cliente? cliente.nombre: "Yo");
      }
    return(
        <Fragment>
            {
                conversaciones &&
                <TableContainer component={Paper}>
                <Table size="small">
                    <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell align="right">Usuario</TableCell>
                        <TableCell align="right">Fecha Envio</TableCell>
                        <TableCell align="right">Mensaje</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {conversaciones.map((conversa : Conversacion) => (
                        <TableRow key={conversa.idConversacion}>
                        <TableCell component="th" scope="row">
                            {conversa.idConversacion}
                        </TableCell>
                        <TableCell align="right">{getUserName(conversa.usuario)}</TableCell>
                        <TableCell align="right">{conversa.fechaConversacion}</TableCell>
                        <TableCell align="right">{conversa.mensaje}</TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
                </TableContainer>
            }
        </Fragment>
    )
}


function DetailConversacion(props: {conversacion : ConversacionesType}){
    const { 
        fechaPost,
        postPrincipal,
        imagenPostPrincipal,
        conversaciones } = props.conversacion;

    return(
        <Paper>
            <DialogTitle style={{ minWidth: 300 }}>
                {postPrincipal}
            </DialogTitle>
            <DialogTitle style={{ minWidth: 300 }}>
                {fechaPost}
            </DialogTitle>
            <Grid
                container
                direction="row"
                justify="center"
                alignItems="center"
            >
                <img 
                    src={imagenPostPrincipal}
                    width="233" height="300"
                    alt="detailConversacion"
                />
            </Grid>
        </Paper>
    );
}

interface EditarConversacionProps{ 
    idHilo : number, 
    dUsuarioToken: string, 
    handleDialogClose : any, 
    open : boolean, 
    updateDConversacion : any, 
    fetchIdDConversacion : any, 
    dConversacion : ConversacionesType,
    dClientesList: ClientesType[]
}

function EditarConversacion(props : EditarConversacionProps) {
    const { idHilo, 
            dConversacion, 
            dUsuarioToken, 
            updateDConversacion, 
            fetchIdDConversacion, 
            handleDialogClose, 
            open,
            dClientesList
        } = props;
    const [conversacion, setConversacion] = useState<ConversacionesType>();
    
    useEffect(() => {
        if( idHilo > 0){
            fetchIdDConversacion(idHilo, dUsuarioToken);
        }
    }, [idHilo]);
    
    useEffect(() => {
        setConversacion(dConversacion);
    }, [dConversacion]);

    return (
        <Fragment>
        <Dialog open={open} onClose={handleDialogClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
        <Grid
        container
        direction="column"
        justify="space-evenly"
        alignItems="center"
        >
            <Grid
            container
            direction="row"
            justify="space-evenly"
            alignItems="flex-start"
            >
            { conversacion && <DetailConversacion conversacion={conversacion} /> }
            { conversacion && <MessagesTable conversaciones={conversacion.conversaciones} dClientesList={dClientesList}></MessagesTable>}
            </Grid>
        <Formik
            initialValues={{
                mensaje: ""
            }}
            validate={(values) => {
            const errors: Partial<{mensaje: string}> = {};
            !values.mensaje && (errors.mensaje = 'Requerido');
            return errors;
            }}
            onSubmit={(values, {setSubmitting}) => {
            setSubmitting(false);
            updateDConversacion({idHilo: idHilo, conversacion:{ mensaje: values.mensaje}}, 
                dUsuarioToken, 
                ()=>console.log("Mensaje enviado satisfactoriamente..."));
            handleDialogClose();
            }}
        >
        {({submitForm, isSubmitting}) => (
        <Form>
            <DialogTitle id="form-dialog-title" style={{ minWidth: 300 }}>Responder a Conversación</DialogTitle>
            <DialogContent>
            <DialogContentText>
                Enviar un mensaje
            </DialogContentText>
            <Grid container direction="column" spacing={1}>
                {isSubmitting && <LinearProgress />}
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="mensaje"
                    type="text"
                    label="Mensaje"
                    variant="outlined"
                    multiline
                    rowsMax={25}
                    disabled={(conversacion && conversacion.estado !== 1)}
                />
                </Grid>
            </Grid>
            </DialogContent>
            <DialogActions>
                <Box margin={1}>
                    <Button onClick={handleDialogClose} color="primary">
                        Cancelar
                    </Button>
                    {/*
                    <Button
                    variant="contained"
                    color="primary"
                    disabled={ isSubmitting || (conversacion && conversacion.estado !== 1)}
                    onClick={() => updateDisableDConversacion({idHilo: idHilo,estado: 0}, () => console.log("Estado deshabilitado"))}
                    >
                    Terminar Conversación
                    </Button>
                    */
                    }
                    <Button
                    variant="contained"
                    color="primary"
                    disabled={isSubmitting || (conversacion && conversacion.estado !== 1)}
                    onClick={submitForm}
                    >
                    Enviar
                    </Button>
                </Box>          
            </DialogActions>
            </Form>
            )}
        </Formik>
        </Grid>
        </Dialog>
        </Fragment>
    );
}
 
  const mapStateToProps = (state : any, props : { idHilo : number, dClientesList: ClientesType[], dUsuarioToken: string, handleDialogClose : any, open : boolean}) => ({
    ...props,
    dConversacion: state.dConversaciones.checkConversacion
  })
  
  const mapActionToProps = {
    updateDConversacion: actions.Update,
    fetchIdDConversacion: actions.fetchById
  }

export default connect(mapStateToProps, mapActionToProps)(EditarConversacion);