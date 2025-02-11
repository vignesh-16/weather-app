import { useState } from "react";
const Home = ()=> {
    const [userDetails , setUserDetails] = useState(JSON.parse(localStorage.getItem("userData")));
    const printUserDetails = ()=> {
        let fields = [];
        for(let detail in userDetails) {
            let field = <div>{detail}: {userDetails[detail]}</div>;
            fields.push(field);
        }
        return fields;
    }
    return (
        <>
            <div className="home-page-starter">
                <span>Welcome to weather app!</span>
                <br/>
                <span>{printUserDetails()}</span>
            </div>
        </>
    )
}

export default Home;