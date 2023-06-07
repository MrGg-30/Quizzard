import React, { useState, useRef } from 'react';
import axios from 'axios';
import { config } from '../../Constants'
import { Button } from '../Button';
import './SignUp.css';
import AuthService  from '../../service/AuthService'

const SignUpModal = ({ onClose }) => {

  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const authService = new AuthService();

  const handleModalClose = () => {
    onClose(); // Call the onClose prop passed from the parent component
  };


  const handleSignUp = () => {
    const requestBody = {
        username: username,
        email: email,
        password: password
    };
    const url = `${config.url.API_BASE_URL}/user/create`
    axios.post(url, requestBody, )
      .then(response => {
        // user created and logged in ? 
        authService.getToken(username, password)
            .then(token => {
                authService.storeToken();
                console.log('Token obtained:', token);
            })
            .catch(error => {
                console.error('Error:', error);
            });
            console.log(authService.loadToken)
            console.log(response.data);
      })
      .catch(error => {
        console.error(error);
      });
    };

    // Handgling drag around
    const modalRef = useRef(null);
    let offsetX, offsetY;

    const handleMouseDown = (event) => {
        offsetX = event.clientX - modalRef.current.offsetLeft;
        offsetY = event.clientY - modalRef.current.offsetTop;
        document.addEventListener('mousemove', handleMouseMove);
        document.addEventListener('mouseup', handleMouseUp);
      };
    
      const handleMouseMove = (event) => {
        modalRef.current.style.left = event.clientX - offsetX + 'px';
        modalRef.current.style.top = event.clientY - offsetY + 'px';
      };
    
      const handleMouseUp = () => {
        document.removeEventListener('mousemove', handleMouseMove);
        document.removeEventListener('mouseup', handleMouseUp);
      };


  

  return (
    <div className="modal" ref={modalRef}>
      <div className="modal-header" onMouseDown={handleMouseDown}>
        <h2 className="modal-title">Sign Up</h2>
        <span className="modal-close" onClick={handleModalClose}>
          &times;
        </span>
      </div>
      <div className="modal-body">
        
        <input type="text" className="modal-input" placeholder="Username" onChange={e => setUsername(e.target.value)} />
        <input type="email" className="modal-input" placeholder="Email" onChange={e => setEmail(e.target.value)} />
        <input type="password" className="modal-input" placeholder="Password" onChange={e => setPassword(e.target.value)} />

        <Button className="modal-button" onClick={handleSignUp}>Sign Up</Button>
      </div>
    </div>
  );
};

export default SignUpModal;
