import { APIServicios } from "./api";

const ACTION_TYPES = {
    CREATE_SER: 'CREATE_SER',
    FETCH_ALL_SER: 'FETCH_ALL_SER'
}
/*
const formateData = (data : any) => ({
    ...data,
    age: parseInt(data.age ? data.age : 0)
})
*/
const fetchAll = (token: string) => ( dispatch : any) => {
    APIServicios().fetchAll(token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_SER,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

const Create = (data : any, token : string , onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIServicios().create(data, token)
        .then(res => {
            dispatch({
                type: ACTION_TYPES.CREATE_SER,
                payload: res.data
            })
            onSuccess()
        })
        .catch(err => console.log(err))
}

//TODO: ACTION_TYPES.FETCH_FOLIO_SER

export { ACTION_TYPES }
export { fetchAll, Create }