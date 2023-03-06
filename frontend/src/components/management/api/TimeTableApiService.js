import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/workorders'
    }
);

export const retrieveAllWorkOrdersApi
    = () => apiClient.get(``)

// export const retrieveFilteredWorkOrders
//     = (userId,after,before) => apiClient.get(`?userId=${userId}&before=${before}&after=${after}`)

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
