import axios from 'axios'
import { getAuthHeaders } from './AuthHeaders';

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/statistics'
    }
);

export const getLastMonthProfit = () => {
  return apiClient.get('/lastMonthProfit', { headers: getAuthHeaders() });
};

export const getTotalProfit = () => {
  return apiClient.get('/totalProfit', { headers: getAuthHeaders() });
};

export const getMonthlyEarningsForLastTwoYears = () => {
  return apiClient.get('/monthly-earnings/last-two-years', { headers: getAuthHeaders() });
};

export const getTop3EmployeesByMoney = (year, month) => {
  return apiClient.get(`/top3EmployeesByMoney/${year}/${month}`, { headers: getAuthHeaders() });
};
