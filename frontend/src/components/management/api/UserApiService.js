import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/users'
    }
);

export const retrieveUserApi
    = () => apiClient.get(``)
