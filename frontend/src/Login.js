const Login = ()=> {
    return (
        <>
            <div className="login-section-container">
                <div className="user-credentials-section">
                    <input type="text"  name="userid" id="userid" placeholder="Enter email or username"></input>
                    <input type="password" name="password" id="password" placeholder="Enter password"></input>
                </div>
                <div className="verify-action-section">
                    <button className="login-click">login</button>
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