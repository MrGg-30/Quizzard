
import Keycloak from 'keycloak-js';
import { config } from './Constants';

const keycloak = new Keycloak({
    url: `${config.url.KEYCLOAK_BASE_URL}`,
    realm: 'quizzard',
    clientId: 'quizzard-app',
});

export default keycloak;