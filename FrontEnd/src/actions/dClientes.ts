import { APIClientes } from "./api";

const ACTION_TYPES = {
    CREATE_CLI: 'CREATE_CLI',
    UPDATE_CLI: 'UPDATE_CLI',
    FETCH_ALL_CLI: 'FETCH_ALL_CLI'
}
/*
const formateData = (data : any) => ({
    ...data,
    age: parseInt(data.age ? data.age : 0)
})
*/
const fetchAll = (token : string) => (dispatch : any) => {
    APIClientes().fetchAll(token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CLI,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

const Create = (data : any, token : string, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIClientes().create(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.CREATE_CLI,
                payload: res.data
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

const Update = (data : any, token :string, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIClientes().update(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.UPDATE_CLI,
                payload: { ...data }
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

export { ACTION_TYPES }
export { fetchAll, Create, Update}