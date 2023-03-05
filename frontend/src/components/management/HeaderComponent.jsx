import {Link} from 'react-router-dom'
import { useAuth } from './security/AuthContext'
import Navbar from 'react-bootstrap/Navbar';
// import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';

import './HeaderComponent.css';

function HeaderComponent() {

    const authContext = useAuth()
    const isAuthenticated = authContext.isAuthenticated
    const username = authContext.username

    function logout() {
        authContext.logout()
    }

    return (
        <header className="border-bottom border-light border-5 mb-3 p-2">
        {/* <Container> */}
        <Navbar collapseOnSelect>
            <Nav className="me-auto">Check In</Nav>
            <Nav>Check out</Nav>
        </Navbar>
            <Navbar collapseOnSelect expand="lg" bg="light">
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        {isAuthenticated &&
                        <Nav.Link as={Link} to={`/welcome/${username}`}>Home</Nav.Link>
                        }
                        {isAuthenticated &&
                        <NavDropdown title={<><i className="bi-calendar3"></i> Work Orders</>} id="collasible-nav-dropdown">
                            <Nav.Link as={Link} to="/activeWorkOrders" className='nav-link' style={{width:'10vw'}}><i className="bi-alarm"></i>Your Work Orders</Nav.Link>
                            <Nav.Link as={Link} to="/WorkOrders" className='nav-link'>All Work Orders</Nav.Link>
                            <Nav.Link as={Link} to="/activeWorkOrders2" className='nav-link'>RecycleBin</Nav.Link>
                        </NavDropdown>}
                        {isAuthenticated &&
                            <Nav.Link as={Link} to="/todos"><i className="bi-calendar3"/> Timetable</Nav.Link>
                        }
                        {isAuthenticated &&
                            <Nav.Link as={Link} to="/todos"><i className="bi-wallet2"/> Payroll</Nav.Link>
                        }
                        {isAuthenticated &&
                            <Nav.Link as={Link} to="/logout" onClick={logout}><i className="bi-graph-up"/> Dashboard</Nav.Link>
                        }
                    </Nav>
                    <Nav>

                        {isAuthenticated &&
                            <Nav.Link as={Link} to="/logout" onClick={logout}>Logout</Nav.Link>
                        }
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
            {/* </Container> */}
    </header>
    )
}

export default HeaderComponent