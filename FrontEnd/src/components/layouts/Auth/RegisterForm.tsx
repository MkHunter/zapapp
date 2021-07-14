import React from 'react';
import { InputAdornment } from "@material-ui/core";
import { Button, 
        Grid, 
        TextField as MuiTextField } from "@material-ui/core";
import { Formik, Form, Field } from 'formik';
import { fieldToTextField,
         TextFieldProps } from 'formik-material-ui';

import { connect } from "react-redux";
import * as actions from "../../../actions/dUsuario";
import { AccountCircle, 
        Lock,
        PhoneAndroid,
        Email
		  } from '@material-ui/icons';
import { Link } from 'react-router-dom';
import { useHistory } from "react-router-dom";

interface Values {
    nombreUsuario: string;
    contraseña: string;
	confirmaContraseña: string;
	
    nombre: string;
    apellido: string;
    telefono: string;
    correo: string;
}
 
function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

function RegisterForm(props : { registerDUsuario : any}) {
	const { registerDUsuario } = props;
	const history = useHistory();

    return (
        <Formik
            initialValues={{
                nombreUsuario: "",
				contraseña: "",
				confirmaContraseña: "",
                nombre: "",
                apellido: "",
                telefono: "",
                correo: ""
              }}
            validate={(values) => {
            const errors: Partial<Values> = {};
			!values.nombreUsuario && (errors.nombreUsuario = 'Requerido');
            !values.contraseña && (errors.contraseña = 'Requerido');

            !values.nombre && (errors.nombre = 'Requerido');
            !values.apellido && (errors.apellido = 'Requerido');
			!values.correo && (errors.correo = 'Requerido');
			
			// Ya se encarga del teléfono vacío
			if(!/^[1-9]\d{9}$/.test(values.telefono.toString())){
                errors.telefono = '# Teléfono inválido'
            }
            
            if(!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.correo)) {
                errors.correo = 'Correo inválido';
			}
			
			if(values.confirmaContraseña !== values.contraseña){
				errors.confirmaContraseña = 'Contraseña no concuerda';
			}
            return errors;
            }}
            onSubmit={(values, {setSubmitting}) => {

            setTimeout(() => {
				setSubmitting(false);
				registerDUsuario({rol: 2,nombreUsuario: values.nombreUsuario, contraseña: values.contraseña,
					entidad: {
					nombre: values.nombre,
					apellido: values.apellido,
					telefono: values.telefono,
					correo: values.correo
					}
				},(isSuccessful : boolean) => {
					if(isSuccessful){
						alert("Registrado satisfactoriamente...");
						history.push("/login");
					}else{
						alert("Datos de registro ya existentes, por favor introduzca otros datos");
					}
				});
            }, 300);
            }}
        >
        {({submitForm, isSubmitting}) => (
        <Form>
            <Grid item>
			<Field
				component={SimpleTextField}
				name="nombreUsuario"
				type="text"
				label="Nombre de usuario"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						    <InputAdornment position="start">
								<AccountCircle/>
							</InputAdornment>
						)
					}}
                />
			</Grid>
			<Grid item>
			<Field
				component={SimpleTextField}
				name="contraseña"
				type="password"
				label="Contraseña"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						<InputAdornment position="start">
							<Lock/>
						</InputAdornment>
					)
				}}
            />
			</Grid>
			<Grid item>
			<Field
				component={SimpleTextField}
				name="confirmaContraseña"
				type="password"
				label="Confirmar Contraseña"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						<InputAdornment position="start">
							<Lock/>
						</InputAdornment>
					)
				}}
            />
			</Grid>
            <Grid item>
			<Field
				component={SimpleTextField}
				name="nombre"
				type="text"
				label="Nombre/s"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						<InputAdornment position="start">
							<AccountCircle/>
						</InputAdornment>
					)
				}}
            />
			</Grid>
            <Grid item>
            <Field
				component={SimpleTextField}
				name="apellido"
				type="text"
				label="Apellido/s"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						<InputAdornment position="start">
							<AccountCircle/>
						</InputAdornment>
					)
				}}
            />
			</Grid>
            <Grid item>
            <Field
				component={SimpleTextField}
				name="telefono"
				type="text"
				label="Teléfono"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						<InputAdornment position="start">
							<PhoneAndroid/>
						</InputAdornment>
					)
				}}
            />
			</Grid>
            <Grid item>
            <Field
				component={SimpleTextField}
				name="correo"
				type="text"
				label="Correo Electrónico"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						<InputAdornment position="start">
							<Email/>
						</InputAdornment>
					)
				}}
            />
			</Grid>
			<Button
				color="primary"
				variant="contained"
				style={{ textTransform: 'none' }}
                disabled={isSubmitting}
                onClick={submitForm}
			>
				Registrarme
            </Button>
			<div style={{ height: 10 }} />
			<Link to="/login">
				¿Ya tienes cuenta?
			</Link>
            </Form>
            )}
        </Formik>
    );
}

const mapActionToProps = {
    registerDUsuario: actions.Register
 }

export default connect(null, mapActionToProps)(RegisterForm);

/*
Access to XMLHttpRequest at 'http://localhost:5000/api/Login/' from origin 'http://localhost:3000' has been blocked by CORS policy: Request header field content-type is not allowed by Access-Control-Allow-Headers in preflight response.
*/

/*
<Grid item>
			<Field
				component={SimpleTextField}
				name="confirmaContraseña"
				type="password"
				label="Confirmar Contraseña"
				margin="normal"
				style={{ textTransform: 'none' }}
				InputProps={{
					startAdornment: (
						<InputAdornment position="start">
							<Lock/>
						</InputAdornment>
					)
				}}
            />
			</Grid>
 */