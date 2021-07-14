import { ACTION_TYPES } from "../actions/dClientes";
const initialStateClientes = {
    listClientes: []
}

export const dClientes = (state = initialStateClientes, action : any) => {
    switch (action.type) {
        case ACTION_TYPES.FETCH_ALL_CLI:
            return {
                ...state,
                listClientes: [...action.payload]
            }
            
        case ACTION_TYPES.CREATE_CLI:
            return {
                ...state,
                listClientes: [...state.listClientes, action.payload]
            }

        case ACTION_TYPES.UPDATE_CLI:
            return {
                ...state,
                listClientes: state.listClientes.map((x : any) => x.identificador === action.payload.identificador ? action.payload : x)
            }            
        default:
            return state
    }
}