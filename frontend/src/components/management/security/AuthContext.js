import { createContext, useContext, useState } from "react";

//1: Create a Context
export const AuthContext = createContext()

export const useAuth = () => useContext(AuthContext)

//2: Share the created context with other components
export default function AuthProvider({ children }) {

    //3: Put some state in the context
    const [isAuthenticated, setAuthenticated] = useState(false)

    const [userId, setUserId] = useState(null)

    const [username, setUsername] = useState(null)

    function login(username, password) {
        if(username==='Godel' && password==='1234'){
            setAuthenticated(true)
            setUsername(username)
            setUserId(10)
            return true            
        } else {
            setAuthenticated(false)
            setUsername(null)
            setUserId(null)
            return false
        }        
    }

    function logout() {
        setAuthenticated(false)
    }

    return (
        <AuthContext.Provider value={ {isAuthenticated, login, logout, username, userId}  }>
            {children}
        </AuthContext.Provider>
    )
} 