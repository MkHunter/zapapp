import { ACTION_TYPES } from "../actions/dCatalogo";
const initialStateCatalogo = {
    listCatalogo: [],
    checkCatalogo: {}
}

export const dCatalogo = (state = initialStateCatalogo, action : any) => {
    switch (action.type) {
        case ACTION_TYPES.FETCH_ALL_CAT:
            return {
                ...state,
                listCatalogo: [...action.payload]
            }
            
        case ACTION_TYPES.CREATE_CAT:
            return {
                ...state,
                listCatalogo: [...state.listCatalogo, action.payload]
            }

        case ACTION_TYPES.UPDATE_CAT:
            return {
                ...state/*,
                listCatalogo: state.listCatalogo.map((x : any) => x.idHilo === action.payload.idHilo ? action.payload : x)
                
                listCatalogo: state.listCatalogo.map(
                    (x : any) => x.idHilo === action.payload.idHilo ? 
                    {...x, catalogo: x.catalogo.push({})} 
                    : x)*/
            }
        case ACTION_TYPES.FETCH_BYID_CAT:
            return {
                ...state,
                checkCatalogo: action.payload
        }

        default:
            return state
    }
}