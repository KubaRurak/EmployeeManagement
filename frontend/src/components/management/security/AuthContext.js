import { createContext, useContext, useState } from "react";
import axios from 'axios';

const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8080/api/v1/'
    }
);

export const AuthContext = createContext()

export const useAuth = () => useContext(AuthContext)

export default function AuthProvider({ children }) {

    const [isAuthenticated, setAuthenticated] = useState(false)
    const [userId, setUserId] = useState(null)
    const [username, setUsername] = useState(null)
    const [role, setRole] = useState(null)

    

    function fetchUserDetails(email, token) {
        apiClient.get(`users/by-email?emailId=${email}`, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                const userDetails = response.data;
                setUsername(`${userDetails.firstName} ${userDetails.lastName}`);
                setUserId(userDetails.userId);
                setRole(userDetails.role);
            })
            .catch(error => {
                console.error('Error fetching user details:', error);
            });
    }

    function login(username, password) {
        return new Promise((resolve, reject) => {
            apiClient.post('auth/login', {
                emailId: username,
                password: password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                const token = response.data.accessToken;
                localStorage.setItem('token', token);
                setAuthenticated(true);
                fetchUserDetails(username, token);
                resolve(true);
            })
            .catch(error => {
                setAuthenticated(false);
                setUsername(null);
                setUserId(null);
                setRole(null);
                reject(false);
            });
        });
    }

    function logout() {
        localStorage.removeItem('token');
        setAuthenticated(false);
        setUsername(null);
        setUserId(null);
        setRole(null); // Reset role on logout
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout, username, userId, role}}>
            {children}
        </AuthContext.Provider>
    )
}