import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import LogoutComponent from './LogoutComponent'
import HeaderComponent from './HeaderComponent'
import ListTodosComponent from './ListTodosComponent'
import ErrorComponent from './ErrorComponent'
import WelcomeComponent from './WelcomeComponent'
import LoginComponent from './LoginComponent'
import TodoComponent from './TodoComponent'
import ListActiveWorkOrdersComponent from './ListActiveWorkOrdersComponent'
import ListWorkOrdersComponent from './ListWorkOrdersComponent'
import AuthProvider, { useAuth } from './security/AuthContext'
import './EmployeeManagementApp.css'
import FilterTableComponent from './FilterTableComponent'

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
                        
                        <Route path='/welcome/:username' element={
                            <AuthenticatedRoute>
                                <WelcomeComponent />
                            </AuthenticatedRoute> 
                        } />
                        
                        <Route path='/todos' element={
                            <AuthenticatedRoute>
                                <ListTodosComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/WorkOrders' element={
                            <AuthenticatedRoute>
                                <ListWorkOrdersComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/activeWorkOrders' element={
                            <AuthenticatedRoute>
                                <ListActiveWorkOrdersComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/activeWorkOrders2' element={
                            <AuthenticatedRoute>
                                <FilterTableComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/todo/:id' element={
                            <AuthenticatedRoute>
                                <TodoComponent /> 
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
