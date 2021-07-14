import React, { Fragment } from 'react';
import { Dialog,
         DialogActions,
         DialogContent,
         DialogTitle } from "@material-ui/core";
import { Box, Button, Grid, TextField as MuiTextField, LinearProgress } from "@material-ui/core";
import { Formik, Form, Field } from 'formik';
import { fieldToTextField,
         TextFieldProps } from 'formik-material-ui';

import { connect } from "react-redux";
import * as actions from "../../../actions/dServicios";
interface Values {
    nombreServicio : string;
    rangoMin : string;
    rangoMax : string;
}

function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

function CrearServicio(props : { createDServicio: any, dUsuarioToken : string}) {
    const { createDServicio, dUsuarioToken } = props;
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
            Crear Servicio
        </Button>
        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
        <Formik
            initialValues={{
            nombreServicio: '',
            rangoMin: 0,
            rangoMax: 0
            }}
            validate={(values) => {
            const errors: Partial<Values> = {};
            if (!values.nombreServicio) {
                errors.nombreServicio = 'Requerido';
            } 
            if (!values.rangoMin){
                errors.rangoMin = 'Requerido';
            }
            if (!values.rangoMax){
                errors.rangoMax = 'Requerido';
            }
            if (!/^[+-]?\d+(\.\d+)?$/.test(values.rangoMin.toString())) {
                errors.rangoMin = 'Minimo inválido'
            }
            if (!/^[+-]?\d+(\.\d+)?$/.test(values.rangoMax.toString())) {
                errors.rangoMax = 'Máximo inválido'
            }
            if (values.rangoMax < values.rangoMin) {
                errors.rangoMax = 'Máximo debe ser mayor que mínimo'
                errors.rangoMin = 'Minimo debe ser menor que máximo'
            }
            return errors;
            }}
            onSubmit={(values, {setSubmitting}) => {
            setSubmitting(false);
            createDServicio(values, dUsuarioToken,()=>console.log("Servicio creado satisfactoriamente..."));
            handleClose(); //Cerrar la ventana de dialogo
            }}
        >
        {({submitForm, isSubmitting}) => (
        <Form>
            <DialogTitle id="form-dialog-title">Crear un Servicio</DialogTitle>
            <DialogContent>
            <Grid container direction="column" spacing={1}>
                <Grid item>
                </Grid>
                {isSubmitting && <LinearProgress />}
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="nombreServicio"
                    type="text"
                    label="Nombre Servicio"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="rangoMin"
                    type="number"
                    label="Costo Mínimo"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="rangoMax"
                    type="number"
                    label="Costo Máximo"
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
                    Crear
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
    createDServicio: actions.Create
}
export default connect(null, mapActionToProps)(CrearServicio);