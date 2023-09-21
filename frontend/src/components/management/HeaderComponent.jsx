import { Link } from 'react-router-dom'
import { useAuth } from './security/AuthContext'
import Navbar from 'react-bootstrap/Navbar';
// import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { useState, useEffect } from "react"
import { checkInApi, checkOutApi, getCheckInApi, getCheckOutApi } from './api/TimeTableApiService';
import './HeaderComponent.css';

function HeaderComponent() {

    const authContext = useAuth()
    const isAuthenticated = authContext.isAuthenticated
    const userRole = authContext.role;
    const username = authContext.username
    const userId = authContext.userId

    const [checkedInAt, setCheckedInAt] = useState(null);
    const [checkedOutAt, setCheckedOutAt] = useState(null);



    useEffect(() => {
        if (!userId) {
            return;
        }
        Promise.all([
            getCheckInApi(userId),
            getCheckOutApi(userId)
        ])
            .then(([checkInResponse, checkOutResponse]) => {
                setCheckedInAt(checkInResponse.data);
                setCheckedOutAt(checkOutResponse.data);
            })
            .catch(error => console.log(error));
    }, [userId]);


    const handleCheckInClick = async (event) => {
        // Stop the event from propagating up to parents
        event.stopPropagation();
    
        try {
            await checkInApi(userId);
            const currentTime = new Date().toLocaleTimeString();
            setCheckedInAt(currentTime);
        } catch (error) {
            console.error(error);
        }
    }
    
    const handleCheckOutClick = async (event) => {
        // Stop the event from propagating up to parents
        event.stopPropagation();
    
        try {
            await checkOutApi(userId);
            const currentTime = new Date().toLocaleTimeString();
            setCheckedOutAt(currentTime);
        } catch (error) {
            console.error(error);
        }
    }

    function logout() {
        authContext.logout()
    }


    return (
        isAuthenticated &&
        <header className="b-0 p-0">
            <Navbar collapseOnSelect expand="lg" bg="light">
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav" style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Nav style={{ paddingLeft: '50px' }}>
                        <NavDropdown className="left-dropdown" title={<><i className="bi-calendar3"></i> Work Orders</>} id="collasible-nav-dropdown">
                            <Nav.Link as={Link} to="/activeworkorders" className='nav-link wide-nav-link'><i className="bi-alarm"></i> Your Work Orders</Nav.Link>
                            <Nav.Link as={Link} to="/workorders" className='nav-link wide-nav-link'><i className="bi-database"></i> All Work Orders</Nav.Link>
                            <Nav.Link as={Link} to="/cancelledworkorders" className='nav-link wide-nav-link'><i className="bi-card-checklist"></i> Unassigned Work Orders</Nav.Link>
                        </NavDropdown>
                        <Nav.Link as={Link} to="/timetables"><i className="bi-calendar3" /> Timetable</Nav.Link>
                        <Nav.Link as={Link} to="/payroll"><i className="bi-wallet2" /> Payroll</Nav.Link>
                        {(userRole === "Admin" || userRole === "Operator") && (
                            <Nav.Link as={Link} to={"/dashboard"}><i className="bi-graph-up" /> Dashboard</Nav.Link>
                        )}
                    </Nav>
                    <Nav style={{ paddingRight: '50px' }}>
                        <NavDropdown className="right-dropdown" align="end" title={<><i className="bi-person-circle"></i> {username || "Profile"}</>} id="user-nav-dropdown">
                            <div className={`nav-link wide-nav-link ${checkedInAt ? 'disabled' : ''}`}>
                                {checkedInAt ? (
                                    `Checked in at ${checkedInAt}`
                                ) : (
                                    <NavDropdown.Item className="no-hover-effect" onClick={handleCheckInClick}>Check In</NavDropdown.Item>
                                )}
                            </div>

                            {checkedInAt && !checkedOutAt && (
                                <div className={`nav-link wide-nav-link`}>
                                    <NavDropdown.Item className="no-hover-effect" onClick={handleCheckOutClick}>Check Out</NavDropdown.Item>
                                </div>
                            )}

                            {checkedInAt && checkedOutAt && (
                                <div className={`nav-link wide-nav-link disabled`}>
                                    Checked out at {checkedOutAt}
                                </div>
                            )}

                            <NavDropdown.Divider />
                            <Nav.Link as={Link} to="/logout" onClick={logout} className='nav-link wide-nav-link'>Logout</Nav.Link>
                        </NavDropdown>
                    </Nav>

                </Navbar.Collapse>
            </Navbar>
        </header>
    )
}

export default HeaderComponent