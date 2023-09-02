import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/statustypes'
    }
);

export const retrieveStatusTypesApi
    = () => apiClient.get(``)
