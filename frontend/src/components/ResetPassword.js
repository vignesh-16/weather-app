import { useState } from 'react';
import { ENDPOINT } from '../globalVariables';
import useGet from '../hooks/useGet'

const ResetPassword = ()=> {

    const [userEmail, setUserEmail] = useState(null);
    const [showText, setShowText] = useState("Confirm Email");
    const [thisStep, setThisStep] = useState('isUserExists')
    const getUserDetails = useGet(ENDPOINT.GET_USER_INFO);

    const nextClick = async()=>{
        document.getElementsByClassName('load-screen-reset-page')[0].classList.add('loader');
        document.getElementsByClassName('next-step')[0].classList.add('button-blue');
        if(thisStep === 'isUserExists' && userEmail !== null) {
            let response = await getUserDetails(userEmail);
            console.log('[RESET][INFO] Response from server: ',response);
            setShowText('Send verification code');
        } else {
            setShowText('Confirm verification code');
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
                    <button className={`button next-step ${thisStep}`} id='next' onClick={ (e)=>{ e.preventDefault(); nextClick() } } >
                        <span className="load-screen-reset-page"></span>
                        <span className='button-label'>{showText}</span>
                    </button>
                </div>
            </div>
        </>
    )
}

export default ResetPassword;