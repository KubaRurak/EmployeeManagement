import axios from 'axios'

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/timetables'
    }
);

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

  export const checkInApi
  = (userId) => apiClient.put(`/${userId}/checkin`)

  export const checkOutApi
  = (userId) => apiClient.put(`/${userId}/checkout`)

  export const getCheckInApi
  = (userId) => apiClient.get(`/${userId}/checkin`)

  export const getCheckOutApi
  = (userId) => apiClient.get(`/${userId}/checkout`)
