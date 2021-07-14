import { APIPedidos } from "./api";

const ACTION_TYPES = {
    CREATE_PED: 'CREATE_PED',
    UPDATE_PED: 'UPDATE_PED',
    FETCH_ALL_PED: 'FETCH_ALL_PED',
    FETCH_BYID_PED: 'FETCH__BYID_PED'
}

const fetchAll = (token: string) => (dispatch : any) => {
    APIPedidos().fetchAll(token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_PED,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

const fetchById = (folioPedido : number, token : string) => (dispatch : any) => {
    APIPedidos().fetchById(folioPedido, token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_BYID_PED,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

const Create = (data : any, token :string, onSuccess : any) => (dispatch : any) => {
    APIPedidos().create(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.CREATE_PED,
                payload: res.data
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

const Update = (data : any, token :string, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIPedidos().update(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.UPDATE_PED,
                payload: data
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

export { ACTION_TYPES }
export { fetchAll, Create, Update, fetchById}