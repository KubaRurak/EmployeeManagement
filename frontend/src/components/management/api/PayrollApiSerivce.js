import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/payroll'
    }
);

export const getFilteredPayrolls = (userId, payrollMonth) => {
    const params = {};
    if (userId) {
      params.userId = userId;
    }
    if (payrollMonth) {
      params.payrollMonth = payrollMonth;
    }
    return apiClient.get('',{ params });
  };

