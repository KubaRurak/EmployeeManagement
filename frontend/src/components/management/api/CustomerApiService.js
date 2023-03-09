import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/customers'
    }
);

export const retrieveCustomersApi
    = () => apiClient.get(``)
