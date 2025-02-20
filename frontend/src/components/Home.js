import { useState } from "react";
import Utils from '../Utilities/Utility';

const Home = () => {
    const [userDetails, setUserDetails] = useState(() => JSON.parse(localStorage.getItem("userData")) || {});
    const [defaultLocation, setDefaultLocation] = useState({});
    const [weatherSubsets, setWeatherSubsets] = useState([]);

    const printUserDetails = () => {
        let fields = [];

        if (userDetails.defaultLocation) {
            let extractedData = Utils.extractDefaultLocation(userDetails.defaultLocation);
            if (!extractedData["parsing-error"] && !extractedData["invalid-data"]) {
                fields.push(
                    <div key="defaultLocation" className={`for-defaultLocation-part`}>
                        <span className="location-container">Results for: {extractedData.location}</span>
                        <span className="location-weather-status">{extractedData.status}</span>
                    </div>
                );
            }
        }

        if (userDetails.keepsTrackOf) {
            Object.entries(userDetails.keepsTrackOf).forEach(([subset, value]) => {
                fields.push(
                    <div key={subset} className={`for-${subset}-part`}>
                        {subset}: {value}
                    </div>
                );
            });
        }

        return fields;
    };

    return (
        <div className="home-page-container">
            <div className="home-page-starter">
                <span>Welcome to the weather app!</span>
                <br />
            </div>
            <div className="weather-portion-container">{printUserDetails()}</div>
        </div>
    );
};

export default Home;
