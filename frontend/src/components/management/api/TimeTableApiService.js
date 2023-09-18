import axios from 'axios'
import { getAuthHeaders } from './AuthHeaders';

const apiClient = axios.create(
  {
    baseURL: 'http://localhost:8080/api/v1/timetables'
  }
);

export const getFilteredTimeTableApi = (userId, after, before) => {
  const params = {};
  if (userId) {
    params.userId = userId;
  }
  if (after && before) {
    params.after = after;
    params.before = before;
  }
  return apiClient.get('', { params, headers: getAuthHeaders() });
};

export const editCheckTimesForDateApi = (userId, date, checkIn, checkOut) => {
  return apiClient.put(`/${userId}/${date}/editTimes?checkIn=${checkIn}&checkOut=${checkOut}`, {}, {
    headers: getAuthHeaders()
  });
};

export const checkInApi = (userId) => {
  return apiClient.put(`/${userId}/checkin`, {}, {
    headers: getAuthHeaders()
  });
}

export const checkOutApi = (userId) => {
  return apiClient.put(`/${userId}/checkout`, {}, {
    headers: getAuthHeaders()
  });
}

export const getCheckInApi = (userId) => {
  return apiClient.get(`/${userId}/checkin`, {
    headers: getAuthHeaders()
  });
}

export const getCheckOutApi = (userId) => {
  return apiClient.get(`/${userId}/checkout`, {
    headers: getAuthHeaders()
  });
}
