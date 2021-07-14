import { APIUsuario } from "./api";

const ACTION_TYPES = {
    USR_LOGIN: 'USR_LOGIN',
    USR_REGISTER: 'USR_REGISTER',
    USR_LOGOUT: 'USR_LOGOUT',
}
/*
const formateData = (data : any) => ({
    ...data,
    age: parseInt(data.age ? data.age : 0)
})
*/

const Login = (loginData : any, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIUsuario().login(loginData)
        .then(res => {
            const { usuario, mensaje, status, token } = res.data;
            dispatch({
                type: ACTION_TYPES.USR_LOGIN,
                payload: {userData: usuario,isAuthenticated: status,token: token}
            })
            onSuccess(status, mensaje as string)
        })
        .catch(err => console.log(err))
}

const Register = (registerData : any, onSuccess : any) => (dispatch : any) => {
    //data = formateData(data)
    APIUsuario().register(registerData)
        .then(res => {
            const isSuccessful : boolean = res.data;
            onSuccess(isSuccessful);
        })
        .catch(err => console.log(err))
}

const Logout = (onSuccess : any) => (dispatch : any) => {
    dispatch({
        type: ACTION_TYPES.USR_LOGIN,
        payload: {
            isAuthenticated: false,
            userData: {},
            token: ""
        }
    });
    onSuccess();
}

export { ACTION_TYPES }
export { Login, Register, Logout }