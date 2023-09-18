import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import LogoutComponent from './LogoutComponent'
import HeaderComponent from './HeaderComponent'
import ErrorComponent from './ErrorComponent'
import LoginComponent from './LoginComponent'
import ListActiveWorkOrdersComponent from './WorkOrder/ListActiveWorkOrdersComponent'
import ListAllWorkOrdersComponent from './WorkOrder/ListAllWorkOrdersComponent'
import ListCancelledWorkOrdersComponent from './WorkOrder/ListCancelledWorkOrdersComponent'
import AuthProvider, { useAuth } from './security/AuthContext'
import './EmployeeManagementApp.css'
import TimetableComponent from './Timetable/TimetableComponent'
import PayrollComponent from './Payroll/PayrollComponent'
import DashboardComponent from './DashboardComponent'

export default function EmployeeManagementApp() {


    return (
            <AuthProvider>
                <BrowserRouter>
                    <AppContent />
                </BrowserRouter>
            </AuthProvider>
    );
}

function AppContent() {

    const authContext = useAuth();
    const appClassName = authContext.isAuthenticated ? "EmployeeManagementApp" : "EmployeeManagementApp no-header";

    return (
        <div className={appClassName}>
            {authContext.isAuthenticated && <HeaderComponent />}
            <Routes>
                <Route path='/' element={ <LoginComponent /> } />
                <Route path='/login' element={ <LoginComponent /> } />

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
                        <Route path='/cancelledworkorders' element={
                            <AuthenticatedRoute>
                                <ListCancelledWorkOrdersComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/timetables' element={
                            <AuthenticatedRoute>
                                <TimetableComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/payroll' element={
                            <AuthenticatedRoute>
                                <PayrollComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/logout' element={
                            <AuthenticatedRoute>
                                <LogoutComponent /> 
                            </AuthenticatedRoute>
                        } />
                        <Route path='/dashboard' element={
                            <AuthenticatedRoute>
                                <DashboardComponent /> 
                            </AuthenticatedRoute>
                        } />
                        
                        <Route path='*' element={<ErrorComponent /> } />
            </Routes>
        </div>
    );
}

function AuthenticatedRoute({ children }) {
    const authContext = useAuth();

    if(authContext.isAuthenticated) {
        return children;
    }
    return <Navigate to="/" replace />;
}
