const Utils = {

    formatDate: (dateString) => {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    },

    capitalizeFirstLetter: (string) => {
        return string.charAt(0).toUpperCase() + string.slice(1);
    },

    generateRandomNumber: (min, max) => {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    },

    extractDefaultLocation: (locationDetails)=> {
        if(locationDetails === null || locationDetails === undefined) {
            return { 'parsing-error': true }
        } else if (typeof(locationDetails) !== "object") {
            return { 'invalid-data': true}
        }
        let location = Object.keys(locationDetails)[0];
        let weatherStatus = locationDetails[location];
        return { location: location, status: weatherStatus };
    }
};

export default Utils;
