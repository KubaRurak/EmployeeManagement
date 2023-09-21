import axios from 'axios'
import { getAuthHeaders } from './AuthHeaders';

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/users'
    }
);

export const retrieveEmployeeAmount = () => {
    return apiClient.get('/amount', { headers: getAuthHeaders() });
};


export const retrieveUserApi = () => {
    return apiClient.get('', { headers: getAuthHeaders() });
};

export const fetchUserByEmailApi = (emailId) => {
    return apiClient.get(`/by-email`, {
        params: { emailId },
        headers: getAuthHeaders()
    });
};





