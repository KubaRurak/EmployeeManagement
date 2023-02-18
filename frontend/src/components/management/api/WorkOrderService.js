import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/workorders'
    }
);

export const retrieveAllWorkOrders
    = () => apiClient.get(``)

// export const retrieveFilteredWorkOrders
//     = (userId,after,before) => apiClient.get(`?userId=${userId}&before=${before}&after=${after}`)

export const getFilteredWorkOrders = (userId, after, before) => {
    const params = {};
    if (userId) {
      params.userId = userId;
    }
    if (after && before) {
      params.after = after;
      params.before = before;
    }
    return apiClient.get({ params });
  };

  export const getActiveWorkOrders = (userId) => {
    const params = {};
    if (userId) {
      params.userId = userId;
    }
    return apiClient.get('/active', { params });
};
