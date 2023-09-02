import {BrowserRouter, Route, Routes} from "react-router-dom";
import BigHeader from "./layouts/BigHeader";
import Master from "./layouts/Master";
import Dashboard from "./pages/Dashboard";
import PlayWithFriend from "./pages/PlayWithFriend";
import PlayStart from "./pages/PlayStart";
import GameResults from "./pages/GameResults";
import LeaderBoard from "./pages/LeaderBoard";
import Friends from "./pages/Friends";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<BigHeader/>}>
                    <Route path="/" element={<Dashboard/>}/>
                </Route>

                <Route element={<Master/>}>
                    <Route path="/play-with-friends" element={<PlayWithFriend/>}/>
                    <Route path="/play-start" element={<PlayStart/>}/>
                    <Route path="/game-results" element={<GameResults/>}/>
                    <Route path="/leader-board" element={<LeaderBoard/>}/>
                    <Route path="/friends" element={<Friends/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
