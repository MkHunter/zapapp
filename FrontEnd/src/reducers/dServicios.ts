import { ACTION_TYPES } from "../actions/dServicios";
const initialStateServicios = {
    listServicios: []
}
//TODO: ACTION_TYPES.FETCH_FOLIO_SER
export const dServicios = (state = initialStateServicios, action : any) => {
    switch (action.type) {
        case ACTION_TYPES.FETCH_ALL_SER:
            return {
                ...state,
                listServicios: [...action.payload]
            }

        case ACTION_TYPES.CREATE_SER:
            return {
                ...state,
                listServicios: [...state.listServicios, action.payload]
            }
        default:
            return state
    }
}