const SimplePopup = ({message}) => {
    const close = ()=> {
        document.getElementsByClassName('popup-dialog')[0].classList.remove('show');
        document.getElementById("simple-popup").classList.add("close");
        document.getElementById('simple-popup').classList.remove("show");
    }
    return (
        <>
            <div className='pop-up-overlay' id='simple-popup'>
                <div className='popup-dialog'>
                    <div className='popup-header-holder'>
                        <button className='close-popup-button' onClick={ (e)=>{ close() } }>x</button>
                    </div>
                    <div className='popup-body-holder'>
                        <span className='popup-content-holder'>{message}</span>
                    </div>
                </div>
            </div>
        </> 
    );
}
 
export default SimplePopup;