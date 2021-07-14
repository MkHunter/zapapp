import { ACTION_TYPES } from "../actions/dReportes";
const initialStateClientes = {
    listReportes: []
}

export const dReportes = (state = initialStateClientes, action : any) => {
    switch (action.type) {
        case ACTION_TYPES.FETCH_ALL_REP:
            return {
                ...state,
                listReportes: [...action.payload]
            }

        default:
            return state
    }
}