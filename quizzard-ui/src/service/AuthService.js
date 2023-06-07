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

      // axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

      const response = await axios.post(config.url.KEYCLOAK_TOKEN_URL, formData.toString(), {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      });

      if (response.status === 200) {
        this.token = response.data.token;
        return this.token;
      } else {
        throw new Error('Unable to get token');
      }
    } catch (error) {
      throw new Error('Unable to get token');
    }
  }

  storeToken() {
    // Store the token in local storage or any other storage mechanism of your choice
    localStorage.setItem('token', this.token);
  }

  loadToken() {
    // Load the token from local storage or any other storage mechanism of your choice
    this.token = localStorage.getItem('token');
    return this.token;
  }
}

export default AuthService
