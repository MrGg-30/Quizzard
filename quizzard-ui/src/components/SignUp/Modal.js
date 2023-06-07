import React, { useState } from 'react';
import AuthService from '../../service/AuthService';
import axios from 'axios';
import { config } from '../../Constants'
import './Modal.css';

const Modal = ({ onClose }) => {
  const [activeTab, setActiveTab] = useState('login');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const authService = new AuthService();

  const handleSubmit = (e) => {
    setError('')
    e.preventDefault();

    if (activeTab === 'login') {
      // Perform login logic here
      // ...

      // Assuming login is successful, display success message and close the modal
      setSuccessMessage('Login successful!');
      setTimeout(() => {
        onClose();
      }, 2000);
    } else {
      if (username.trim() === '' || password.trim() === '' || confirmPassword.trim() === '') {
        setError('Please fill in all fields.');
        return;
      }

      if (password !== confirmPassword) {
        setError('Passwords do not match.');
        return;
      }

      if (password.length < 8) {
        setError('Password must be at least 8 characters long.');
        return;
      }
    console.log("sign up?????")
      // Perform signup logic here
      // ...
      const requestBody = {
        username: username,
        email: email,
        password: password
        };
        const url = `${config.url.API_BASE_URL}/user/create`
        axios.post(url, requestBody)
            .then(response => {
            // user created and logged in ? 
            authService.getToken(username, password)
                .then(token => {
                    authService.storeToken();
                    // console.log('Token obtained:', token);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            console.log(response)
            if(response.status === 201) {
                setSuccessMessage('Sign up successful!');
                setTimeout(() => {
                onClose();
            }, 2000);
            }

            })
            .catch(error => {
                console.error(error);
                setError(error.response.data)
                return;
            });
      
    }
  };

  const handleTabChange = (tab) => {
    setActiveTab(tab);
    setSuccessMessage('');
    setError('');
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <div className="tabs">
          <div
            className={`tab ${activeTab === 'login' ? 'active' : ''}`}
            onClick={() => handleTabChange('login')}
          >
            Login
          </div>
          <div
            className={`tab ${activeTab === 'signup' ? 'active' : ''}`}
            onClick={() => handleTabChange('signup')}
          >
            Sign Up
          </div>
        </div>
        <h2>{activeTab === 'login' ? 'Log In' : 'Sign Up'}</h2>
        {error && <p className="error-message">{error}</p>}
        {successMessage && <p className="success-message">{successMessage}</p>}
        <form onSubmit={handleSubmit}>
          <label htmlFor="email">Email:</label>
          <input
            type="text"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          {activeTab === 'signup' && (
            <div>
                <label htmlFor="username">Username:</label>
                <input
                    type="text"
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />    
            </div>
          )}
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          {activeTab === 'signup' && (
            <div>
              <label htmlFor="confirmPassword">Confirm Password:</label>
              <input
                type="password"
                id="confirmPassword"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </div>
          )}

          <button type="submit">{activeTab === 'login' ? 'Log In' : 'Sign Up'}</button>
        </form>
        <button className="close-button" onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
};

export default Modal;
