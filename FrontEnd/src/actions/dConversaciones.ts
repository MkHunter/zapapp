import { APIConversaciones } from "./api";

const ACTION_TYPES = {
    CREATE_CON: 'CREATE_CON',
    UPDATE_CON: 'UPDATE_CON',
    UPDATE_DIS_CON: 'UPDATE_DIS_CON',
    DELETE_CON: 'DELETE_CON',
    FETCH_ALL_CON: 'FETCH_ALL_CON',
    FETCH_BYID_CON: 'FETCH_BYID_CON'
}

const fetchAll = (token : string) => (dispatch : any) => {
    APIConversaciones().fetchAll(token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CON,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

const fetchById = (idHilo : number,token : string) => (dispatch : any) => {
    APIConversaciones().fetchById(idHilo, token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_BYID_CON,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

const Update = (data : { idHilo: number, conversacion: { mensaje: string }}, token :string, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIConversaciones().update(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.UPDATE_CON,
                payload: data
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

const UpdateDisable = (data : { idHilo: number, estado: number}, token :string, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIConversaciones().updateDisable(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.UPDATE_DIS_CON,
                payload: data
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

export { ACTION_TYPES }
export { fetchAll, fetchById, Update, UpdateDisable }