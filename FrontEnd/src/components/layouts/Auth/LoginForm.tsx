import React from 'react';
import { InputAdornment} from "@material-ui/core";
import { Button, Grid, TextField as MuiTextField } from "@material-ui/core";
import { Formik, Form, Field } from 'formik';
import { fieldToTextField,
         TextFieldProps } from 'formik-material-ui';

import { connect } from "react-redux";
import * as actions from "../../../actions/dUsuario";
import { AccountCircle, Lock
		  } from '@material-ui/icons';
import { useHistory } from "react-router-dom";

interface Values {
    usuario: string;
    contraseña: string;
}
 
function SimpleTextField(props: TextFieldProps) {
    return <MuiTextField {...fieldToTextField(props)} />;
}

function LoginForm(props : { loginDUsuario : any}) {
	const { loginDUsuario } = props;
	const history = useHistory();
    return (
        <Formik
            initialValues={{
				usuario: "",
				contraseña: ""
			}}
            validate={(values) => {
            const errors: Partial<Values> = {};
			!values.usuario && (errors.usuario = 'Requerido');
			!values.usuario && (errors.contraseña = 'Requerido');
            return errors;
            }}
            onSubmit={(values, {setSubmitting}) => {
            setTimeout(() => {
				setSubmitting(false);
				loginDUsuario(values,(isSuccessful : boolean, mensaje : string) => {
					if(isSuccessful){
						history.push("/inicio");
					}else{
						alert(mensaje);
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
					name="usuario"
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
				<Button
					color="primary"
					variant="contained"
					style={{ textTransform: 'none' }}
					disabled={isSubmitting}
                    onClick={submitForm}
				>
					Iniciar sesión
				</Button>
				<div style={{ height: 10 }} />
				<Button 
					color="primary" 
					variant="outlined" 
                    style={{ textTransform: 'none' }}
                    onClick={() => history.push("/registro")}
				>
					Registrarse
				</Button>
            </Form>
            )}
        </Formik>
    );
}

const mapActionToProps = {
    loginDUsuario: actions.Login
}

export default connect(null, mapActionToProps)(LoginForm);