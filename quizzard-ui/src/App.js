import {BrowserRouter, Route, Routes} from "react-router-dom";
import keycloak from './keycloak'
import BigHeader from "./layouts/BigHeader";
import Master from "./layouts/Master";
import Dashboard from "./pages/Dashboard";
import PlayWithFriend from "./pages/PlayWithFriend";
import PlayStart from "./pages/PlayStart";
import GameResults from "./pages/GameResults";
import LeaderBoard from "./pages/LeaderBoard";
import Friends from "./pages/Friends";
import Profile from "./pages/Profile";
import {Dimmer, Header, Icon, Loader} from 'semantic-ui-react'
import {ReactKeycloakProvider} from '@react-keycloak/web'
import {Api} from "./api";
import PrivateRoute from "./components/PrivateRoute"
import PageNotFound from "./pages/404";
import { useState } from "react";

function App() {
    const [user, setUser] = useState(undefined)

    const initOptions = {pkceMethod: 'S256'}
    const handleOnEvent = async (event, error) => {
        if (event === 'onAuthSuccess') {
            if (keycloak.authenticated) {
                try {
                    const response = await Api.getUserByToken(keycloak.token);

                    if (response.status === 200) {
                        setUser(response.data);
                    }
                    if (response.status === 204) {
                        await Api.createUser(keycloak);
                        const res = await Api.getUserByToken(keycloak.token);
                        if(res.data) {
                            setUser(res.data);
                        }
                    }
                } catch (error) {
                    console.error("An error occurred:", error);
                }
            }                  
        }

    }

    const loadingComponent = (
        <Dimmer inverted active={true} page>
            <Header style={{color: '#4d4d4d'}} as='h2' icon inverted>
                <Header.Content style={{ height: '100vh', width: '100vw', display: 'grid', placeItems: 'center' }}>
                    Application is loading...
                </Header.Content>
            </Header>
        </Dimmer>
    )
    return (
        <ReactKeycloakProvider
            authClient={keycloak}
            initOptions={initOptions}
            LoadingComponent={loadingComponent}
            onEvent={(event, error) => handleOnEvent(event, error)}
        >
            <BrowserRouter>
                <Routes>
                    <Route element={<BigHeader keycloak={keycloak} />}>
                        <Route path="/" element={<Dashboard />}/>
                    </Route>

                    <Route element={<Master/>}>
                        <Route path="/play-with-friends" element={<PrivateRoute><PlayWithFriend keycloak={keycloak} user={user}/></PrivateRoute>}/>
                        <Route path="/play-start" element={<PrivateRoute><PlayStart/></PrivateRoute>}/>
                        <Route path="/game-results" element={<PrivateRoute><GameResults/></PrivateRoute>}/>
                        <Route path="/leader-board" element={<PrivateRoute><LeaderBoard keycloak={keycloak} /></PrivateRoute>}/>
                        <Route path="/friends" element={<PrivateRoute><Friends keycloak={keycloak} user={user}/></PrivateRoute>}/>
                        <Route path="/profile" element={<PrivateRoute><Profile keycloak={keycloak} user={user} /></PrivateRoute>}/>
                        <Route path="*" element={<PrivateRoute><PageNotFound /></PrivateRoute>}/>
                    </Route>
                </Routes>
            </BrowserRouter>
        </ReactKeycloakProvider>
    );
}

export default App;