import React, { Fragment, SetStateAction, useEffect, useState } from 'react';
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
import * as actions from "../../../actions/dCatalogo";
interface Values {
    marca: string,
    color: string,
    precio: string,
    talla: string
}

function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

function CrearCatalogo(props : { createDCatalogo: any, dUsuarioToken : string}) {
    const { createDCatalogo, dUsuarioToken } = props;
    const [open, setOpen] = React.useState(false);
    const [baseImage, setBaseImage] = useState("");

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    
    const uploadImage = async (e : any) => {
        console.log(e.target.files[0]);
        if(e.target.files[0].type === 'image/jpeg' || e.target.files[0].type === 'image/png'){
            const file : File = e.target.files[0];
            const base64 = await convertBase64(file);
            setBaseImage(base64 as SetStateAction<string>);
        }
    };

    const convertBase64 = (file : File) => {
        return new Promise((resolve, reject) => {
            const fileReader = new FileReader();
            fileReader.readAsDataURL(file);

            fileReader.onload = () => {
                resolve(fileReader.result);
                return fileReader.result;
            };

            fileReader.onerror = (error) => {
                reject(error);
                console.log(error);
            };
        });
    };

    return (
        <Fragment>
        <Button variant="contained" color="primary" onClick={handleClickOpen}>
            Registrar Calzado
        </Button>
        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
        <Formik
            initialValues={{
                marca: "",
                color: "",
                precio: 0,
                talla: 0
            }}
            validate={(values) => {
            const errors: Partial<Values> = {};
            !values.marca && (errors.marca = 'Requerido');
            !values.color && (errors.color = 'Requerido');
            !(values.precio > 0) && (errors.precio = 'Requerido');
            !(values.talla > 0) && (errors.talla = 'Requerido');
            return errors;
            }}
            onSubmit={(values, {setSubmitting}) => {
                if(!baseImage){
                    alert("Imagen es requerida");
                }else{
                    createDCatalogo({...values, imagen: baseImage}, dUsuarioToken,()=>console.log("Calzado creado satisfactoriamente..."));
                    handleClose();
                }
                setSubmitting(false);
            }}
        >
        {({submitForm, isSubmitting}) => (
        <Form>
            <DialogTitle id="form-dialog-title" style={{ minWidth: 300 }}>Registrar Calzado en Catálogo de Remate</DialogTitle>
            <DialogContent>
            <DialogContentText>
                Datos personales
            </DialogContentText>
            <Grid container direction="column" spacing={1}>
                {isSubmitting && <LinearProgress />}
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="marca"
                    type="text"
                    label="Marca"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="color"
                    type="text"
                    label="Color"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="talla"
                    type="number"
                    label="Talla"
                    variant="outlined"
                />
                </Grid>
                <Grid item>
                <Field
                    component={SimpleTextField}
                    name="precio"
                    type="number"
                    label="Precio"
                    variant="outlined"
                />
                </Grid>
                <input
                    type="file"
                    onChange={(e : any) => {
                    uploadImage(e);
                    }}
                />
                <img src={baseImage} height="200px" />
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
                    Registrar
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
    createDCatalogo: actions.Create
}
export default connect(null, mapActionToProps)(CrearCatalogo);