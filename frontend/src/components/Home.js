import { useState } from "react";
import Utils from '../Utilities/Utility';
const Home = ()=> {
    const [userDetails , setUserDetails] = useState(JSON.parse(localStorage.getItem("userData")));
    const [defaultLocation, setDefaultLocation] = useState({});
    const [weatherSubsets, setWeatherSubsets] = useState([]);

    const printUserDetails = ()=> {
        let fields = [];
        for(let detail in userDetails) {
            if (detail === 'defaultLocation') {
                let extractedData = Utils.extractDefaultLocation(userDetails[detail]);
                if (! extractedData["parsing-error"] && !extractedData["invalid-data"]) {
                    setDefaultLocation(extractedData);
                    let field = <div key={detail} className={`for-${detail}-part`}>
                            <span className="location-container">Results for: ${defaultLocation.location}</span>
                            <span className="location-weather-status">${defaultLocation.status}</span>
                        </div>;
                    fields.push(field);
                }
            } else if (detail === 'keepsTrackOf') {
                for(let subset in userDetails[detail]) {
                    let field = <div key={subset} className={`for-${subset}-part`}>{subset}: {userDetails[detail][subset]}</div>;
                    fields.push(field);
                }
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