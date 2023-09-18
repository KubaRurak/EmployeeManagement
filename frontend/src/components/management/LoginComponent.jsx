import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from './security/AuthContext'
import './LoginComponent.css';

function LoginComponent() {

  const [username, setUsername] = useState('admin@yourcompany.com')

  const [password, setPassword] = useState('')
  const [loginAttempted, setLoginAttempted] = useState(false);
  const [loggingIn, setLoggingIn] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false)

  const navigate = useNavigate()

  const authContext = useAuth()

  function handleUsernameChange(event) {
    setUsername(event.target.value)
  }

  function handlePasswordChange(event) {
    setPassword(event.target.value)
  }

  function handleSubmit() {
    authContext.login(username, password)
    .then(success => {
        if(success) {
            navigate(`/activeWorkOrders`);
        } else {
            setShowErrorMessage(true);
        }
    })
    .catch(() => {
        setShowErrorMessage(true);
    });
}

  return (
    <div className="Auth-form-container">
      <form className="Auth-form">
        <div className="Auth-form-content">
          {showErrorMessage && <div className="errorMessage">Authentication Failed.
            Please check your credentials.</div>}
          <h3 className="Auth-form-title">Sign In</h3>
          <div className="form-group mt-2">
            <label>Email address</label>
            <input
              type="username"
              className="form-control mt-1"
              placeholder="Enter email"
              value={username}
              onChange={handleUsernameChange}
            />
          </div>
          <div className="form-group mt-2">
            <label>Password</label>
            <input
              type="password"
              className="form-control mt-1"
              placeholder="Enter password"
              value={password}
              onChange={handlePasswordChange}
            />
          </div>
          <div className="d-grid gap-2 mt-3">
            <button type="button" name="login" className="btn btn-primary" onClick={handleSubmit}>
              Submit
            </button>
          </div>
          <p className="forgot-password text-right mt-2">
            Forgot <a href="#">password?</a>
          </p>
        </div>
      </form>
    </div>
  )
}

export default LoginComponent



