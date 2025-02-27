import { useState } from 'react';

const ResetPassword = ()=> {

    const [userEmail, setUserEmail] = useState(null);
    const [showText, setShowText] = useState("Send verification code");
    const [thisStep, setThisStep] = useState('isUserExists')

    const nextClick = async()=>{
        if(thisStep === 'isUserExists') {
            console.log('Make a back-end call to check if user exists with provided email!');
            setShowText('Confirm verification code');
        } else {
            console.log('Verify the inserted verification code is valid or not');
        }
    }

    return (
        <>
            <div className="reset-password-section-container">
                <div className="user-credentials-section">
                    <input type="text" className={`${userEmail === 'invalid' ? 'is-invalid' : ''}`} name="userid" id="userid" onChange={ (e)=>{ setUserEmail(e?.currentTarget?.value) } } placeholder="Enter your email"></input>
                </div>
                <div className='next-step-button-holder'>
                    <button className={`next-step ${thisStep}`} id='next' onClick={ (e)=>{ e.preventDefault(); nextClick() } } >{showText}</button>
                </div>
            </div>
        </>
    )
}

export default ResetPassword;