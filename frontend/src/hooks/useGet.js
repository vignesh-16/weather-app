const useGet = (BASE_URL) => {
    const getMethod = async(params)=> {
        const REQ_URL = `${BASE_URL}${params}`;
        console.log(`[API-LOGS][INFO]:: Making a get call to '${REQ_URL}'!`)
        let result = {};
        try {
            const response = await fetch(REQ_URL, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            result = response.ok ? await response.json() : null;
            console.info('[API-LOGS][INFO]:: Response from server: ',result);
            return result;
        } catch (e) {
            console.error('[API-LOGS][ERROR]:: Exception occurred while performing a get call to: ',BASE_URL, e);
            return e;
        }
    };
    return getMethod;
}
 
export default useGet;