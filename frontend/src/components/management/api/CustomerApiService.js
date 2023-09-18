import axios from 'axios'
import { getAuthHeaders } from './AuthHeaders';

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/customers'
    }
);

export const retrieveCustomersApi = () => {
    return apiClient.get('', { headers: getAuthHeaders() });
  };