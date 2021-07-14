import { ACTION_TYPES } from "../actions/dConversaciones";
const initialStateConversaciones = {
    listConversaciones: [],
    checkConversacion: {}
}

export const dConversaciones = (state = initialStateConversaciones, action : any) => {
    switch (action.type) {
        case ACTION_TYPES.FETCH_ALL_CON:
            return {
                ...state,
                listConversaciones: [...action.payload]
            }
            
        case ACTION_TYPES.CREATE_CON:
            return {
                ...state,
                listConversaciones: [...state.listConversaciones, action.payload]
            }

        case ACTION_TYPES.UPDATE_CON:
            return {
                ...state/*,
                listConversaciones: state.listConversaciones.map((x : any) => x.idHilo === action.payload.idHilo ? action.payload : x)
                
                listConversaciones: state.listConversaciones.map(
                    (x : any) => x.idHilo === action.payload.idHilo ? 
                    {...x, conversaciones: x.conversaciones.push({})} 
                    : x)*/
            }
        case ACTION_TYPES.FETCH_BYID_CON:
            return {
                ...state,
                checkConversacion: action.payload
        }
        case ACTION_TYPES.UPDATE_DIS_CON:
            return {
                ...state/*,
                listConversaciones: state.listConversaciones.map((x : any) => x.idHilo === action.payload.idHilo ? action.payload : x)
                
                listConversaciones: state.listConversaciones.map(
                    (x : any) => x.idHilo === action.payload.idHilo ? 
                    {...x, conversaciones: x.conversaciones.push({})} 
                    : x)*/
            }

        default:
            return state
    }
}