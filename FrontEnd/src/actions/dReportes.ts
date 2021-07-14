import { APIReportes } from "./api";

const ACTION_TYPES = {
    FETCH_ALL_REP: 'FETCH_ALL_REP'
}

const fetchAll = (token : string) => (dispatch : any) => {
    APIReportes().fetchAll(token)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_REP,
                payload: response.data
            })
        })
        .catch(err => console.log(err))
}

export { ACTION_TYPES }
export { fetchAll }