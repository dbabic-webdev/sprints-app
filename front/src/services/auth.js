import SprintsAxios from "../apis/SprintsAxios"
import jwt_decode from "jwt-decode"

export const login = async function(username, password){
    const data = {
        username: username,
        password: password
    }
    try{
        const ret = await SprintsAxios.post('users/auth', data);
        const decoded = jwt_decode(ret.data);
        window.localStorage.setItem('role', decoded.role.authority);
        window.localStorage.setItem('jwt', ret.data);
    }catch(error){
        console.log(error);
    }
    window.location.assign("/");
}

export const logout = function(){
    window.localStorage.removeItem('jwt');
    window.location.assign("/");
}