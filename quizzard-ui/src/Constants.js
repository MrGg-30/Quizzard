const prod = {
    url: {
      API_BASE_URL: 'some.url.for.prod',
      KEYCLOAK_TOKEN_URL: 'some.url.for.prod.keycloak',
      KEYCLOAK_CLIENT_ID: 'quizzard-app'
    }
  }
  
  const dev = {
    url: {
      API_BASE_URL: 'http://localhost:8080',
      KEYCLOAK_TOKEN_URL: 'http://localhost:8090/realms/quizzard/protocol/openid-connect/token',
      KEYCLOAK_CLIENT_ID: 'quizzard-app'
    }
  }
  
  export const config = process.env.NODE_ENV === 'development' ? dev : prod