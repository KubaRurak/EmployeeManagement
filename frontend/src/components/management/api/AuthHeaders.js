export function getAuthHeaders() {
    const token = localStorage.getItem('token');
    if (!token) return {};  // If no token, return an empty object
    return { 'Authorization': 'Bearer ' + token };
  }