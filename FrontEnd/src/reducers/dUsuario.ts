import { ACTION_TYPES } from "../actions/dUsuario";
const initialState = {
    isAuthenticated: false,
    userData: {},
    token: ""
}

export const dUsuario = (state = initialState, action : any) => {
    switch (action.type) {
        // Falta adaptar esto para recibir los encabezados del login
        case ACTION_TYPES.USR_LOGIN:
            return {
                ...state,
                isAuthenticated: action.payload.isAuthenticated,
                userData: action.payload.userData,
                token: action.payload.token
            }
        // Falta adaptar esto, quizá eliminarlo del todo.
        case ACTION_TYPES.USR_LOGOUT:
            return {
                isAuthenticated: action.payload.isAuthenticated,
                userData: action.payload.userData,
                token: action.payload.token
            }
        default:
            return state
    }
}