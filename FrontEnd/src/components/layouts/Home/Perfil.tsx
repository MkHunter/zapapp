import React, { Fragment } from 'react';
import { Grid, Typography } from '@material-ui/core';
import { connect } from 'react-redux';
import { UserDataType } from "../../../helpers/AppTypes";

function Welcome(props: {dUsuarioIsAuthenticated: boolean ,dUsuarioData : UserDataType, dUsuarioToken : string}) {
  const { dUsuarioIsAuthenticated, dUsuarioData } = props;
  
  const roles: string[] = ["", "Cliente", "Tendero", "Gerente"];
  return (
    <Fragment>
      {
        dUsuarioIsAuthenticated?
        <Fragment>
          <Grid
            container
            direction="column"
            justify="center"
            alignItems="center"
          >
          <Grid item>
            <Typography paragraph variant="h4">
            Información de Usuario
            </Typography>
          </Grid>
          <Grid item>
            <Typography paragraph variant="h6">
              Nombre/s: {dUsuarioData.nombreUsuario}
            </Typography>
            <Typography paragraph variant="h6">
              Apellido/s: {dUsuarioData.nombreUsuario}
            </Typography>
            <Typography paragraph variant="h6">
              NUsuario: {dUsuarioData.nombreUsuario}
            </Typography>
            <Typography paragraph variant="h6">
              Rol: {roles[dUsuarioData.rol]}
            </Typography>
          </Grid>
          <Grid item>
            <Typography paragraph variant="h6">Correo: {dUsuarioData.entidad.correo}</Typography>
          </Grid>
          <Grid item>
            <Typography paragraph variant="h6">Teléfono: {dUsuarioData.entidad.telefono}</Typography>
          </Grid>
        </Grid>
        </Fragment>
        :
        <Fragment>
          <Typography>
            Usted no está autenticado
          </Typography>
        </Fragment>
      }
    </Fragment>
    )
}

const mapStateToProps = (state : any) => ({
  dUsuarioIsAuthenticated: state.dUsuario.isAuthenticated,
  dUsuarioData: state.dUsuario.userData,
  dUsuarioToken: state.dUsuario.token
});

export default connect(mapStateToProps, {})(Welcome);