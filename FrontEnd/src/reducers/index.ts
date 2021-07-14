import { combineReducers } from "redux";
import { dPedidos } from "./dPedidos";
import { dClientes } from "./dClientes";
import { dServicios } from "./dServicios";
import { dUsuario } from "./dUsuario";
import { dConversaciones } from "./dConversaciones";
import { dCatalogo } from "./dCatalogo";
import { dReportes } from "./dReportes";
import sessionStorage from 'redux-persist/lib/storage/session'
import { persistReducer } from "redux-persist";

const reducers = combineReducers({
    dPedidos,
    dClientes,
    dServicios,
    dConversaciones,
    dCatalogo,
    dUsuario,
    dReportes
});

const persistConfig = {
    key:'root',
    storage: sessionStorage,
    whitelist:["dUsuario"]
}

export default persistReducer(persistConfig, reducers);