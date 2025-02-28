const BASE_API = "http://localhost:8080";
const USER_SERVICE = "user"

const ENDPOINT = {
    LOGIN: `${BASE_API}/login`,
    RESET_PASSWORD: `${BASE_API}/resetPassword`,
    CREATE_ACCOUNT: `${BASE_API}/${USER_SERVICE}/create`,
    GET_USER_INFO: `${BASE_API}/${USER_SERVICE}/getUserDetails?userId=`,
    UPDATE_USER_DATA: `${BASE_API}/${USER_SERVICE}/updateUserData`,
}

export { BASE_API, USER_SERVICE, ENDPOINT };