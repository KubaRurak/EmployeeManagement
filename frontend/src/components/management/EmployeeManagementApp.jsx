import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import LogoutComponent from './LogoutComponent'
import HeaderComponent from './HeaderComponent'
import ErrorComponent from './ErrorComponent'
import LoginComponent from './LoginComponent'
import ListActiveWorkOrdersComponent from './ListActiveWorkOrdersComponent'
import ListAllWorkOrdersComponent from './ListAllWorkOrdersComponent'
import AuthProvider, { useAuth } from './security/AuthContext'
import './EmployeeManagementApp.css'
import TimetableComponent from './TimeTableComponent'

function AuthenticatedRoute({children}) {
    const authContext = useAuth()
    
    if(authContext.isAuthenticated)
        return children

    return <Navigate to="/" />
}

export default function EmployeeManagementApp() {
    return (
        <div className="EmployeeManagementApp">
            <AuthProvider>
                <BrowserRouter>
                    <HeaderComponent />
                    <Routes>
                        <Route path='/' element={ <LoginComponent /> } />
                        <Route path='/login' element={ <LoginComponent /> } />
                        
                        <Route path='/activeworkorders' element={
                            <AuthenticatedRoute>
                                <ListActiveWorkOrdersComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/workorders' element={
                            <AuthenticatedRoute>
                                <ListAllWorkOrdersComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/timetables' element={
                            <AuthenticatedRoute>
                                <TimetableComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/logout' element={
                            <AuthenticatedRoute>
                                <LogoutComponent /> 
                            </AuthenticatedRoute>
                        } />
                        
                        <Route path='*' element={<ErrorComponent /> } />

                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </div>
    )
}
