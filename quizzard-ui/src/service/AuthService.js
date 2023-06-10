import axios from 'axios';
import { config } from '../Constants'

class AuthService {
  constructor() {
    this.token = null;
  }

  async getToken(username, password) {
    try {
      const formData = new URLSearchParams();
      formData.append('username', username);
      formData.append('password', password);
      formData.append('client_id', config.url.KEYCLOAK_CLIENT_ID);
      formData.append('grant_type', 'password');

      const response = await axios.post(config.url.KEYCLOAK_TOKEN_URL, formData.toString(), {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      });

      if (response.status === 200) {
        this.token = response.data.access_token;
        return this.token;
      } else {
        throw new Error('Unable to get token');
      }
    } catch (error) {
      throw new Error('Unable to get token');
    }
  }

  storeToken() {
    // Store the token in local storage 
    localStorage.setItem('token', this.token);
  }

  loadToken() {
    // Load the token from local storage
    this.token = localStorage.getItem('token');
    return this.token;
  }
}

export default AuthService
