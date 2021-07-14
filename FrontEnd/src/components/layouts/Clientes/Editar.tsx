import React, { Fragment } from 'react';
import { Dialog,
         DialogActions,
         DialogContent,
         DialogContentText,
         DialogTitle} from "@material-ui/core";
import { Box, Button, Grid, TextField as MuiTextField, LinearProgress } from "@material-ui/core";
import { Formik, Form, Field } from 'formik';
import { fieldToTextField,
         TextFieldProps } from 'formik-material-ui';

import { connect } from "react-redux";
import * as actions from "../../../actions/dClientes";


import { ClientesType } from "../../../helpers/AppTypes";

interface Values {
    nombre: string;
    telefono: string;
    apellido: string;
    correo: string;
}

function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

function Editar(props : { cliente : ClientesType, dUsuarioToken: string, handleDialogClose : any, open : boolean, updateDCliente : any}) {
    const { cliente, dUsuarioToken, updateDCliente, handleDialogClose, open } = props;

    return (
        <Fragment>
        <Dialog open={open} onClose={handleDialogClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
        <Formik
            initialValues={{
                ...cliente
            }}
            validate={(values) => {
            const errors: Partial<Values> = {};
            !values.nombre && (errors.nombre = 'Requerido');
            !values.apellido && (errors.apellido = 'Requerido');
            !values.correo && (errors.correo = 'Requerido');

            if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.correo)) {
                errors.correo = 'Correo electrónico inválido';
            }
            return errors;
            }}
            onSubmit={(values, {setSubmitting}) => {
            setSubmitting(false);
            updateDCliente(values, dUsuarioToken,()=>console.log("Cliente editado satisfactoriamente..."));
            handleDialogClose();
            }}
        >
        {({submitForm, isSubmitting}) => (
        <Form>
            <DialogTitle id="form-dialog-title" style={{ minWidth: 300 }}>Editar un Cliente</DialogTitle>
            <DialogContent>
            <DialogContentText>
                Datos personales
            </DialogContentText>
            <Grid container direction="column" spacing={1}>
                {isSubmitting && <LinearProgress />}
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="identificador"
                    type="number"
                    label="ID"
                    variant="outlined"
                    disabled
                />
                </Grid>
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
                    <Button onClick={handleDialogClose} color="primary">
                    Cancelar
                    </Button>
                    <Button
                    variant="contained"
                    color="primary"
                    disabled={isSubmitting}
                    onClick={submitForm}
                    >
                    Guardar
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
 
  const mapStateToProps = (state : any, props : { cliente : ClientesType, dUsuarioToken: string, handleDialogClose : any, open : boolean}) => ({
    ...props
  })
  
  const mapActionToProps = {
    updateDCliente: actions.Update
  }

export default connect(mapStateToProps, mapActionToProps)(Editar);