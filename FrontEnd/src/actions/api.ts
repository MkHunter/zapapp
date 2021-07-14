import axios from "axios";

//const baseUrl = "http://localhost:5000/api/";
const baseUrl = "http://138.197.198.78:5000/api/"; //TODO: Cambiar en producción

function headerAuthToken(token : string) {
    return({ 'headers': { 'Authorization': `Bearer ${token}`}});
}

function APIServicios(url = baseUrl + 'Servicios/') {
    return {
        fetchAll: (token :string) => axios.get(url,headerAuthToken(token)),
        fetchById: (folio : number, token :string) => axios.get(url + folio, headerAuthToken(token)),
        create: (nuevoServicio : any, token: string) => axios.post(url, nuevoServicio,headerAuthToken(token)),
        update: (folio : number, actualizaServicio : any, token :string) => axios.put(url + folio, actualizaServicio,headerAuthToken(token))
    }
}

function APIPedidos(url = baseUrl + 'Pedidos/') {
    return {
        fetchAll: (token : string) => axios.get(url, headerAuthToken(token)),
        fetchById: (folioPedido : number, token :string) => axios.get(url + folioPedido, headerAuthToken(token)),
        create: (nuevoPedido : any, token :string) => axios.post(url, nuevoPedido, headerAuthToken(token)),
        update: (actualizaEstadoPedido : any, token :string) => axios.put(url, actualizaEstadoPedido, headerAuthToken(token))
    }
}

function APIClientes(url = baseUrl + 'Clientes/') {
    return {
        fetchAll: (token : string) => axios.get(url, headerAuthToken(token)),
        fetchById: (identificador : number, token :string) => axios.get(url + identificador, headerAuthToken(token)),
        create: (identificador : any, token :string) => axios.post(url, identificador, headerAuthToken(token)),
        update: (actualizaCliente : any, token :string) => axios.put(url, actualizaCliente, headerAuthToken(token))
    }
}

function APIConversaciones(url = baseUrl + 'Conversaciones/') {
    return {
        fetchAll: (token : string) => axios.get(url, headerAuthToken(token)),
        fetchById: (idHilo : number, token :string) => axios.get(url + idHilo, headerAuthToken(token)),
        update: (actualizaHilo : { idHilo: number, conversacion: { mensaje: string }}, token :string) => axios.put(url, actualizaHilo, headerAuthToken(token)),
        updateDisable: (deshabilitaHilo : { idHilo: number, estado: number}, token :string) => axios.put(url+'actualizarEstado', deshabilitaHilo, headerAuthToken(token))
    }
}

function APICatalogo(url = baseUrl + 'Catalogo/') {
    return {
        fetchAll: (token : string) => axios.get(url, headerAuthToken(token)),
        fetchById: (idProducto : number, token :string) => axios.get(url + idProducto, headerAuthToken(token)),
        create: (idProducto : any, token :string) => axios.post(url, idProducto, headerAuthToken(token))
    }
}

function APIUsuario(url = baseUrl ) {
    return {
        login: (loginData : any) => axios.post(url + 'Login/', loginData),
        register: (registerData : any) => axios.post(url + 'Register/', registerData)
    }
}

function APIReportes(url = baseUrl + 'Reportes/') {
    return {
        fetchAll: (token : string) => axios.get(url, headerAuthToken(token))
    }
}

export {APIServicios, APIPedidos, APIClientes, APIUsuario, APIConversaciones, APICatalogo, APIReportes}