import axios from 'axios'
import { getAuthHeaders } from './AuthHeaders';

const apiClient = axios.create(
  {
    baseURL: 'http://localhost:8080/api/v1/workorders'
  }
);

export const retrieveWorkOrderNumbers = (status) => {
  const params = status ? { status: status } : {};
  return apiClient.get('/amount', { params, headers: getAuthHeaders() });
};

export const retrieveRecentWorkorders = () => {
  return apiClient.get('/recent', { headers: getAuthHeaders() });
};

export const retrieveProfitByOrderType = () => {
  return apiClient.get('/profit-per-orderType', { headers: getAuthHeaders() });
};


export const getFilteredWorkOrdersApi = (userId, after, before, status) => {
  const params = {};
  if (userId) {
    params.userId = userId;
  }
  if (after && before) {
    params.after = after;
    params.before = before;
  }
  if (status) {
    params.status = status;
  }
  return apiClient.get('', { params, headers: getAuthHeaders() });
};

export const fetchWorkOrdersByIdsApi = (orderIds) => {
  return apiClient.get('/batch', {
    params: { orderIds },
    paramsSerializer: { indexes: null },
    headers: getAuthHeaders()
  });
};

export const getActiveWorkOrdersApi = (userId) => {
  const params = {};
  if (userId) {
    params.userId = userId;
  }
  return apiClient.get('/active', {
    params,
    headers: getAuthHeaders()
  });
};

export const completeWorkOrderApi = (orderId) => {
  return apiClient.put(`${orderId}/complete`, {}, {
    headers: getAuthHeaders()
  });
}

export const editWorkOrderApi = (orderId, workOrder) => {
  return apiClient.put(`${orderId}`, workOrder, {
    headers: getAuthHeaders()
  });
}

export const createWorkOrderApi = (workOrder) => {
  return apiClient.post('', workOrder, {
    headers: getAuthHeaders()
  });
}

export const assignWorkOrderApi = (userId, orderId) => {
  const params = {};
  params.userId = userId;
  params.orderId = orderId;
  return apiClient.put('/assign', {
    params,
    headers: getAuthHeaders()
  });
};