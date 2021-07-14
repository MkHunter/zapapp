import { ACTION_TYPES } from "../actions/dPedidos";
const initialStatePedidos = {
    listPedidos: [],
    checkPedido: {}
}

export const dPedidos = (state = initialStatePedidos, action : any) => {
    switch (action.type) {
        case ACTION_TYPES.FETCH_ALL_PED:
            return {
                ...state,
                listPedidos: [...action.payload]
            }

        case ACTION_TYPES.CREATE_PED:
            return {
                ...state,
                listPedidos: [...state.listPedidos, action.payload]
            }

        case ACTION_TYPES.UPDATE_PED:
            return {
                ...state,
                listPedidos: state.listPedidos.map(
                    (x : any) => x.folioPedido === action.payload.folioPedido ? 
                    {...x, estadoPedido: action.payload.estadoPedido} 
                    : x)
            }
            
        case ACTION_TYPES.FETCH_BYID_PED:
            return {
                ...state,
                checkPedido: action.payload
            }
        
        default:
            return state
    }
}