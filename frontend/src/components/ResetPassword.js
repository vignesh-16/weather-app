import { useState } from 'react';
import { ENDPOINT } from '../globalVariables';
import useGet from '../hooks/useGet'
import SimplePopup from './SimplePopup';

const ResetPassword = ()=> {

    const [userEmail, setUserEmail] = useState(null);
    const [showText, setShowText] = useState("Confirm Email");
    const [thisStep, setThisStep] = useState('isUserExists');
    const [showMessage, setShowMessage] = useState('');
    const getUserDetails = useGet(ENDPOINT.GET_USER_INFO);

    const nextClick = async()=>{
        document.getElementsByClassName('load-screen-reset-page')[0].classList.add('loader');
        document.getElementsByClassName('next-step')[0].classList.add('button-blue');
        document.getElementsByClassName('button-label')[0].classList.add('no-display');
        if(thisStep === 'isUserExists' && userEmail !== null) {
            let response = await getUserDetails(userEmail);
            console.log('[RESET][INFO] Response from server: ',response);
            if (response.ERROR) {
                setShowMessage('No User was found with provided email!');
                setThisStep('isUserExists');
                document.getElementById('simple-popup').classList.add('show');
                document.getElementsByClassName('popup-dialog')[0].classList.add('show');
                document.getElementsByClassName('load-screen-reset-page')[0].classList.remove('loader');
                document.getElementsByClassName('next-step')[0].classList.remove('button-blue');
                document.getElementsByClassName('button-label')[0].classList.remove('no-display');
                setUserEmail('');
                setShowText('Verify email again');
            } else {
                setShowText('Send OTP to mail');
            }
            
        } else {
            //setShowText('Confirm verification code');
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
            < SimplePopup message={showMessage} />
        </>
    )
}

export default ResetPassword;