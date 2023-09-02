import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/workorders'
    }
);

export const retrieveAllWorkOrdersApi
    = () => apiClient.get(``)

export const getFilteredWorkOrdersApi = (userId, after, before) => {
    const params = {};
    if (userId) {
      params.userId = userId;
    }
    if (after && before) {
      params.after = after;
      params.before = before;
    }
    return apiClient.get('',{ params });
  };

  export const getActiveWorkOrdersApi = (userId) => {
    const params = {};
    if (userId) {
      params.userId = userId;
    }
    return apiClient.get('/active', { params });
};

export const completeWorkOrderApi
    = (orderId) => apiClient.put(`${orderId}/complete`)

export const editWorkOrderApi
    = (orderId, workOrder) => apiClient.put(`${orderId}`, workOrder)

export const createWorkOrderApi
    = (workOrder) => apiClient.post('', workOrder)

export const assignWorkOrderApi = (userId, orderId) => {
      const params = {};
      params.userId = userId;
      params.orderId = orderId;
      return apiClient.put('/assign',{ params });
    };