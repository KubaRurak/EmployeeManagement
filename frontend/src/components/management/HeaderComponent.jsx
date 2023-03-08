import {Link} from 'react-router-dom'
import { useAuth } from './security/AuthContext'
import Navbar from 'react-bootstrap/Navbar';
// import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import {useState, useEffect} from "react"
import { checkInApi, checkOutApi, getCheckInApi, getCheckOutApi } from './api/TimeTableApiService';
import './HeaderComponent.css';

function HeaderComponent() {

    const authContext = useAuth()
    const isAuthenticated = authContext.isAuthenticated
    const username = authContext.username
    const userId = authContext.userId

    const [checkedInAt, setCheckedInAt] = useState(null);
    const [checkedOutAt, setCheckedOutAt] = useState(null);


  
    useEffect(() => {
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


    const handleCheckInClick = async () => {
        try {
            await checkInApi(userId);
            const currentTime = new Date().toLocaleTimeString();
            setCheckedInAt(currentTime);
        } catch (error) {
            console.error(error);
        }
    }

    const handleCheckOutClick = async () => {
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
        <header className="border-bottom border-light border-5 mb-3 p-2">
        {/* <Container> */}
        <Navbar collapseOnSelect>
            <Navbar.Collapse id="responsive-navbar-nav">
                {isAuthenticated &&
                <Nav className="me-auto">
                    {checkedInAt ?
                            `Checked in at ${checkedInAt}` :
                            <Nav.Link onClick={handleCheckInClick}>Check In</Nav.Link>
                        }
                    </Nav>}
                    {isAuthenticated && checkedInAt &&(
                    <Nav className="ml-auto">
                        {checkedOutAt ?
                            `Checked out at ${checkedOutAt}` :
                            <Nav.Link onClick={handleCheckOutClick}>Check Out</Nav.Link>
                        }
                </Nav>)}
            </Navbar.Collapse>
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
                            <Nav.Link as={Link} to="/activeWorkOrders" className='nav-link' style={{width:'10vw'}}><i className="bi-alarm"></i> Your Work Orders</Nav.Link>
                            <Nav.Link as={Link} to="/WorkOrders" className='nav-link'><i className="bi-database"></i> All Work Orders</Nav.Link>
                            {/* <Nav.Link as={Link} to="/activeWorkOrders2" className='nav-link'>RecycleBin</Nav.Link> */}
                        </NavDropdown>}
                        {isAuthenticated &&
                            <Nav.Link as={Link} to="/todos"><i className="bi-calendar3"/> Timetable</Nav.Link>
                        }
                        {isAuthenticated &&
                            <Nav.Link as={Link} to="/todos"><i className="bi-wallet2"/> Payroll</Nav.Link>
                        }
                        {isAuthenticated &&
                            <Nav.Link as={Link} to={`/welcome/${username}`}><i className="bi-graph-up"/> Dashboard</Nav.Link>
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