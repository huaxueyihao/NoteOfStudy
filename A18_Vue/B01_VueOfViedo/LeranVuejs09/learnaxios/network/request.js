import axios from 'axios'



export function request(config,success,failure){


    const instance = axios.create({
        baseURL: '',
        timeout: 5000
    })

    instance(config).then(res=>{
        success(res);
    }).catch(err=>{
        failure(err);
    })



}