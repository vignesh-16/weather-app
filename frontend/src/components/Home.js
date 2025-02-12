import { useState } from "react";
const Home = ()=> {
    const [userDetails , setUserDetails] = useState(JSON.parse(localStorage.getItem("userData")));
    const printUserDetails = ()=> {
        let fields = [];
        for(let detail in userDetails) {
            if (detail === 'defaultLocation' || detail === 'keepsTrackOf') {
                let field = <div className={`for-${detail}-part`}>Results for: {userDetails[detail]}</div>;
                fields.push(field);
            }
        }
        return fields;
    }
    return (
        <>
            <div className="home-page-container">
                <div className="home-page-starter">
                    <span>Welcome to weather app!</span>
                    <br/>
                </div>
                <div className="weather-portion-container">{printUserDetails()}</div>
            </div>
        </>
    )
}

export default Home;