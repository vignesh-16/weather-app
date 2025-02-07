const usePost = (endPoint)=>{
    const postMethod = async (params)=>{
        console.info('%c Info: Server params: ','color: blue; font-size: 15px',params);
        let result = {};
        try {
            const response = await fetch(endPoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(params)
            }); 
            result = await response.json();
            console.info('Response from server: ',result);
            return result;
        } catch (err) {
            console.error('%c Error: could not complete post call: ','color: red; font-size: 10px',err)
            return err
        }
    }
    return postMethod;
}

export default usePost;