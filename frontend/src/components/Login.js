import { useState } from "react";
import { ENDPOINT } from "../globalVariables";
import usePost from "../hooks/usePost";
import { useNavigate } from 'react-router-dom';
import SimplePopup from "./SimplePopup";

const Login = ()=> {
    const [ userid, setUserId ] = useState(null);
    const [ password, setPassword ] = useState(null);
    const [ showMessage, setShowMessage ] = useState('Hello Popup!');
    const verifyUser = usePost(ENDPOINT.LOGIN);
    const router = useNavigate();

    const login = async ()=>{
        if((userid !== null && userid !== '') 
            && (password !== null && password !== '')) 
        {
            document.getElementsByClassName('login-click')[0].classList.add('button-blue');
            document.getElementsByClassName('buffer-container')[0].classList.add('loader');
            console.info(`Here are the credentials: userid: ${userid}, password: ${password}`);
            let loginData = {
                email: userid,
                password: password
            }
            let response = await verifyUser(loginData);
            if (response.STATUS === 'SUCCESS' && response?.USER_TOKEN) {
                localStorage.setItem("authToken", response?.USER_TOKEN);
                localStorage.setItem("userData", JSON.stringify(response?.USER_DATA));
                for(let pair in response.USER_DATA) {
                    console.log(pair, response.USER_DATA[pair]);
                }
                document.getElementsByClassName('buffer-container')[0].classList.remove('loader');
                document.getElementsByClassName('login-click')[0].classList.remove('button-blue');
                router('/home');
            } else if (response.STATUS === 'FAILED' && response.MESSAGE === 'Invalid email or password') {
                document.getElementsByClassName('login-click')[0].classList.remove('button-blue');
                document.getElementsByClassName('buffer-container')[0].classList.remove('loader');
                setShowMessage('Invalid user credentials! Please check your email & password');
                document.getElementById('simple-popup').classList.add('show');
                document.getElementsByClassName('popup-dialog')[0].classList.add('show');
            } else {
                document.getElementsByClassName('login-click')[0].classList.remove('button-blue');
                document.getElementsByClassName('buffer-container')[0].classList.remove('loader');
                setShowMessage('Something went wrong! Please try again!');
                document.getElementById('simple-popup').classList.add('show');
                document.getElementsByClassName('popup-dialog')[0].classList.add('show');
            }
        } else {
            if (userid === null) {
                setUserId('invalid');
            }
            if (password === null) {
                setPassword('invalid');
            }
        }
    }
    return (
        <>
            <div className="login-section-container">
                <div className="user-credentials-section">
                    <input type="text" className={`${userid === 'invalid' ? 'is-invalid' : ''}`} name="userid" id="userid" onChange={ (e)=>{ setUserId(e?.currentTarget?.value) } } placeholder="Enter your email"></input>
                    <input type="password" className={`${password === 'invalid' ? 'is-invalid' : ''}`} name="password" id="password" onChange={ (e)=>{ setPassword(e?.currentTarget?.value) } } placeholder="Enter your password"></input>
                </div>
                <div className="verify-action-section">
                    <button className="login-click" onClick={ (e)=>{ e.preventDefault(); login() } }>
                        <span className="buffer-container"></span>
                        login
                    </button>
                    <button className="forgot-password">Forgot Password?</button>
                </div>
                <div className="create-an-account">
                    <button className="create-new-acc">
                        <span className="buffer-container"></span>
                        Create an account!
                    </button>
                </div>
            </div>
            < SimplePopup message={showMessage} />
        </>
    )
}

export default Login;