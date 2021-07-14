import { APICatalogo } from "./api";

const ACTION_TYPES = {
    CREATE_CAT: 'CREATE_CAT',
    UPDATE_CAT: 'UPDATE_CAT',
    FETCH_ALL_CAT: 'FETCH_ALL_CAT',
    FETCH_BYID_CAT: 'FETCH_BYID_CAT'
}
/*
const formateData = (data : any) => ({
    ...data,
    age: parseInt(data.age ? data.age : 0)
})
*/
const fetchAll = (token : string) => (dispatch : any) => {
    APICatalogo().fetchAll(token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CAT,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

const Create = (data : any, token : string, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APICatalogo().create(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.CREATE_CAT,
                payload: res.data
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

const fetchById = (idProducto : number,token : string) => (dispatch : any) => {
    APICatalogo().fetchById(idProducto, token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_BYID_CAT,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

export { ACTION_TYPES }
export { fetchAll, fetchById, Create}