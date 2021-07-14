import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './components/App';
import * as serviceWorker from './serviceWorker';

import { BrowserRouter as Router } from "react-router-dom";

import { Provider } from "react-redux";
import { store, persistor } from "./actions/store";

import { PersistGate } from "redux-persist/integration/react";

import { ThemeProvider } from "@material-ui/core/styles";
import theme from "./theme";

const rootElement = document.getElementById("root");
ReactDOM.render(
    <Provider store={store}>
      <Router>
        <PersistGate persistor={persistor}>
          <ThemeProvider theme={theme}>
            <App />
          </ThemeProvider>
        </PersistGate>
      </Router>
    </Provider>,
  rootElement
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
