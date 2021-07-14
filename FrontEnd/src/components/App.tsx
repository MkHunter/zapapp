import React, { Fragment, CSSProperties } from 'react';
import './App.css';
import Home from './layouts/Home/index';

import Auth from './layouts/Auth/Index';
import LoginForm from './layouts/Auth/LoginForm';
import RegisterForm from './layouts/Auth/RegisterForm';
import { Redirect, Route, Switch } from "react-router-dom";

import Pedidos from './layouts/Pedidos/index';
import Clientes from './layouts/Clientes/index';
import Conversaciones from './layouts/Conversaciones/index';
import Catalogo from './layouts/Catalogo/index';
import Reportes from './layouts/Reportes/';
import Servicios from './layouts/Servicios/';
import Perfil from './layouts/Home/Perfil';
import { connect } from 'react-redux';
import { Grid } from '@material-ui/core';

function GoHome() {
  return (
    <Redirect to="/inicio"/>
  )
}

function App(props: {dUsuarioIsAuthenticated : boolean}){
  const { dUsuarioIsAuthenticated} = props;
    return (
      <Fragment>
        <Switch>
          <Route exact path="/login" render={() => <Auth><LoginForm/></Auth>}>
            {dUsuarioIsAuthenticated && <Redirect to="/" />}
          </Route>
          <Route exact path="/registro" render={() => <Auth><RegisterForm/></Auth>}>
            {dUsuarioIsAuthenticated && <Redirect to="/" />}
          </Route>
          
          <Route exact path="/">
            {dUsuarioIsAuthenticated ? <Redirect to="/inicio" /> : <Redirect to="/login" />}
          </Route>
          <Route exact path="/inicio" render={()=><Home><Grid container justify="center">
                    <img src={`${process.env.PUBLIC_URL}/Inicio.jpg`} style={{height: '100%',
    width: '100%'}} alt="inicio-logo" />
                </Grid></Home>}>
          {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>

          <Route exact path="/pedidos" render={()=><Home><Pedidos/></Home>}>
            {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>
          <Route exact path="/clientes" render={()=> <Home><Clientes/></Home>}>
            {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>
          <Route exact path="/reportes" render={()=> <Home><Reportes/></Home>}>
            {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>
          <Route exact path="/servicios" render={()=> <Home><Servicios/></Home>}>
            {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>
          <Route exact path="/conversaciones" render={()=> <Home><Conversaciones/></Home>}>
            {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>
          <Route exact path="/catalogo" render={()=> <Home><Catalogo/></Home>}>
            {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>
          <Route exact path="/perfil" render={()=><Home><Perfil/></Home>}>
            {!dUsuarioIsAuthenticated && <Redirect to="/login" />}
          </Route>
          <Route component={GoHome}/>
        </Switch>
      </Fragment>
    );
}

const mapStateToProps = (state : any) => ({
  dUsuarioIsAuthenticated: state.dUsuario.isAuthenticated
});

export default connect(mapStateToProps,{})(App);