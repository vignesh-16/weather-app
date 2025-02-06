import { useState } from "react";

const Login = ()=> {
    const [ userid, setUserId ] = useState(null);
    const [ password, setPassword ] = useState(null);

    const login = ()=>{
        if((userid !== null && userid !== '') 
            && (password !== null && password !== '')) 
        {
            console.info(`Here are the credentials: userid: ${userid}, password: ${password}`);
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
                    <input type="text" className={`${userid === 'invalid' ? 'is-invalid' : ''}`} name="userid" id="userid" onChange={ (e)=>{ setUserId(e?.currentTarget?.value) } } placeholder="Enter email or username"></input>
                    <input type="password" className={`${password === 'invalid' ? 'is-invalid' : ''}`} name="password" id="password" onChange={ (e)=>{ setPassword(e?.currentTarget?.value) } } placeholder="Enter password"></input>
                </div>
                <div className="verify-action-section">
                    <button className="login-click" onClick={ (e)=>{ e.preventDefault(); login() } }>login</button>
                    <button className="forgot-password">Forgot Password?</button>
                </div>
                <div className="create-an-account">
                    <button className="create-new-acc">Create an account!</button>
                </div>
            </div>
        </>
    )
}

export default Login;