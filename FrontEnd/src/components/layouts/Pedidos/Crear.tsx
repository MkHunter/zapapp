import React, { useState, useEffect } from 'react';
import { Dialog,
         DialogActions,
         DialogContent,
         DialogTitle,  
         InputAdornment,
         MenuItem, 
         Paper } from "@material-ui/core";
import { Box, Button, Grid, TextField as MuiTextField, LinearProgress } from "@material-ui/core";
import { Formik, Form, Field } from 'formik';
import { fieldToTextField,
         TextFieldProps } from 'formik-material-ui';
import { DateTimePicker } from 'formik-material-ui-pickers';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';
import esLocale from "date-fns/locale/es";
import format from "date-fns/format";
import { Schedule } from '@material-ui/icons';

import * as actionsPedidos from "../../../actions/dPedidos";
import { CreatePedidosType, 
         ServicioType,
         ClientesType } from "../../../helpers/AppTypes";
import * as actionsClientes from "../../../actions/dClientes";
import { connect } from "react-redux";

import EnhancedTable from "./EnhancedTable";

interface Values {
    fechaEstimada: string;
    cliente: string;
    monto: string;
    abonado: string;
    descripcion: string;
}

function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

class LocalizedUtils extends DateFnsUtils {
    getDatePickerHeaderText(date : any) {
      return format(date, "d MM yyyy", { locale: esLocale });
    }
}

interface CrearPedidoProps {
    dClientesList: ClientesType[],
    fetchAllDClientes: any,
    dUsuarioToken : string,
    dUserID: number,
    createDPedido: any
}

function CrearPedido(props : CrearPedidoProps){
    const [dialogOpen,setDialogOpen] = useState(false);
    const { dClientesList, fetchAllDClientes, createDPedido, dUsuarioToken, dUserID } = props;
    const [monto, setMonto] = useState<number>(0);
    const [servicios, setServicios] = useState<ServicioType[]>([]);
    const [initialValues, setInitialValues] = useState<CreatePedidosType>({
        usuario: dUserID,
        cliente: 0,
        fechaEstimada: new Date(),
        monto: monto,
        abonado: 0,
        descripcion: '',
        servicios: servicios
    });
    const handleClickOpen = () => {
        setDialogOpen(true);
    };

    const handleClose = () => {
        setDialogOpen(false);
    };

    useEffect(()=>{
        fetchAllDClientes(dUsuarioToken)
    },[dialogOpen]);

    useEffect(()=>{
        setMonto(servicios.map(serv => serv.costo).reduce((a, b) => a + b, 0));
    },[servicios]);

    useEffect(()=>{
        setInitialValues({
            ...initialValues,
            monto: monto,
        });
    },[monto]);
   return (
    <div>
        <Button variant="contained" color="primary" onClick={handleClickOpen}>Crear Pedido</Button>
        <Dialog open={dialogOpen} onClose={handleClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
            <Formik
                enableReinitialize
                initialValues={initialValues} 
                //TODO: Validar que la fecha actual sea menor a la fecha estimada
                validate={(values) => {
                    const errors: Partial<Values> = {};
                    if (values.cliente < 0){
                        errors.cliente = 'Seleccione un cliente';
                    }
                    if (!/^[+-]?\d+(\.\d+)?$/.test(values.monto.toString())) {
                        errors.abonado = 'Monto inválido'
                    }
                    if (!values.abonado){
                        errors.abonado = 'Campo vacío no válido';
                    }else if (!/^[+-]?\d+(\.\d+)?$/.test(values.abonado.toString())) {
                        errors.abonado = 'Abono inválido';
                    }
                    if (values.abonado > values.monto){
                        errors.abonado = 'Superando límite de abono';
                    }
                    return errors;
                }}
                onSubmit={(values, {setSubmitting}) => {
                    setTimeout(() => {
                        setSubmitting(false);
                        createDPedido({...values, fechaEstimada: values.fechaEstimada.toLocaleString('en-GB').replace(',',''), servicios: servicios}, 
                            dUsuarioToken,
                            () => console.log("Pedido registrado exitosamente"));
                    }, 500);
                    handleClose(); //Cerrar la ventana de dialogo
                }}
            >
            {({submitForm, isSubmitting, touched, errors}) => (
            <MuiPickersUtilsProvider utils={LocalizedUtils} locale={esLocale}>
                <Form>
                <DialogTitle id="form-dialog-title">        Crear un Pedido</DialogTitle>
                <DialogContent>
                <Grid container direction="column" spacing={1}>
                    <Grid item>
                        <EnhancedTable setServicios={setServicios}/>
                    </Grid>
                </Grid>
                <Paper>
                <DialogTitle id="data-dialog-title">Datos del Pedido</DialogTitle>
                <Grid
                    container
                    direction="row-reverse"
                    justify="space-around"
                    alignItems="center"
                >
                    <Grid item>
                        <Field
                            component={SimpleTextField}
                            name="monto"
                            type="number"
                            label="Monto Total"
                            variant="outlined"
                            value={monto}
                            disabled={true}
                            startadornment={<InputAdornment position="start">$</InputAdornment>}
                        />
                        </Grid>
                    <Grid item>
                        <Field
                            component={SimpleTextField}
                            name="abonado"
                            type="number"
                            label="Abono"
                            helperText="Cantidad a abonar"
                            variant="outlined"
                            startadornment={<InputAdornment position="start">$</InputAdornment>}
                        />
                    </Grid>
                </Grid>
                <Grid
                    container
                    direction="row-reverse"
                    justify="space-around"
                    alignItems="center"
                >
                    <Grid item>
                    <Field
                        component={SimpleTextField}
                        type="text"
                        name="cliente"
                        label="Cliente"
                        select
                        helperText="Por favor, intruduzca un Cliente"
                        variant="outlined"
                        InputLabelProps={{
                        shrink: true,
                        }}
                    >
                        {dClientesList.length > 0 && dClientesList.map((cliente : ClientesType) => (
                        <MenuItem key={cliente.identificador} value={cliente.identificador}>
                            {cliente.nombre+" "+cliente.apellido}
                        </MenuItem>
                        ))}
                    </Field>
                    </Grid>
                    <Grid item>
                        <Field
                            component={DateTimePicker}
                            name="fechaEstimada"
                            label="Fecha Entrega"
                            format="d MMM yyyy hh:mm a"
                            inputVariant="outlined"
                            InputProps={{ endAdornment: ( <InputAdornment position="end"> <Schedule/> </InputAdornment> )}}
                        />
                    </Grid>
                </Grid>
                <Field
                    component={SimpleTextField}
                    name="descripcion"
                    multiline
                    label="Descripción"
                    helperText="*Opcional"
                    variant="outlined"
                    rowsMax={4}
                    fullWidth
                />
                </Paper>
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
                </MuiPickersUtilsProvider>
                )}
            </Formik>
        </Dialog>
    </div>
    );
}

const mapStateToProps = (state : any) => ({
    dClientesList: state.dClientes.listClientes,
    dUserID: state.dUsuario.userData.idUsuario
});

const mapActionToProps = {
    fetchAllDClientes: actionsClientes.fetchAll,
    //fetchAllDServicios: actionsServicios.fetchAll,
    createDPedido: actionsPedidos.Create
}

export default connect(mapStateToProps, mapActionToProps)(CrearPedido);