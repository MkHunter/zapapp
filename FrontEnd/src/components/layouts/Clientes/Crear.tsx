import React, { Fragment } from 'react';
import { Dialog,
         DialogActions,
         DialogContent,
         DialogContentText,
         DialogTitle } from "@material-ui/core";
import { Box, Button, Grid, TextField as MuiTextField, LinearProgress } from "@material-ui/core";
import { Formik, Form, Field } from 'formik';
import { fieldToTextField,
         TextFieldProps } from 'formik-material-ui';

import { connect } from "react-redux";
import * as actions from "../../../actions/dClientes";
interface Values {
    nombre : string;
    apellido: string;
    telefono : string;
    correo : string;
}

function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

function CrearCliente(props : { createDCliente: any, dUsuarioToken : string}) {
    const { createDCliente, dUsuarioToken } = props;
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <Fragment>
        <Button variant="contained" color="primary" onClick={handleClickOpen}>
            Crear Cliente
        </Button>
        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
        <Formik
            initialValues={{
                nombre: "",
                apellido: "",
                telefono: "",
                correo: ""
            }}
            validate={(values) => {
            const errors: Partial<Values> = {};
            !values.apellido && (errors.apellido = 'Requerido');
            !values.telefono && (errors.telefono = 'Requerido');
            !values.correo && (errors.correo = 'Requerido');

            // Ya se encarga del teléfono vacío
            if(!/^[1-9]\d{9}$/.test(values.telefono.toString())){
                errors.telefono = '# Teléfono inválido'
            }

            if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.correo)) {
                errors.correo = 'Correo electrónico inválido';
            }
            return errors;
            }}
            onSubmit={(values, {setSubmitting}) => {
            setSubmitting(false);
            createDCliente({entidad: {...values}}, dUsuarioToken,()=>console.log("Cliente creado satisfactoriamente..."));
            handleClose(); //Cerrar la ventana de dialogo
            }}
        >
        {({submitForm, isSubmitting}) => (
        <Form>
            <DialogTitle id="form-dialog-title" style={{ minWidth: 300 }}>Crear un Cliente</DialogTitle>
            <DialogContent>
            <DialogContentText>
                Datos personales
            </DialogContentText>
            <Grid container direction="column" spacing={1}>
                {isSubmitting && <LinearProgress />}
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="nombre"
                    type="text"
                    label="Nombre/s"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="apellido"
                    type="text"
                    label="Apellido/s"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="telefono"
                    type="text"
                    label="# Celular"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="correo"
                    type="text"
                    label="Correo"
                    variant="outlined"
                />
                </Grid>
            </Grid>
            </DialogContent>
            <DialogActions>
                <Box margin={1}>
                    <Button onClick={handleClose} color="primary">
                    Cancelar
                    </Button>
                    <Button
                    variant="contained"
                    color="primary"
                    disabled={isSubmitting}
                    onClick={submitForm}
                    >
                    Crear un Cliente
                    </Button>
                </Box>          
            </DialogActions>
            </Form>
            )}
        </Formik>
        </Dialog>
        </Fragment>
    );
}

const mapActionToProps = {
    createDCliente: actions.Create
}
export default connect(null, mapActionToProps)(CrearCliente);