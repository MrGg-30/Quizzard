import {BrowserRouter, Route, Routes} from "react-router-dom";
import BigHeader from "./layouts/BigHeader";
import Master from "./layouts/Master";
import Dashboard from "./pages/Dashboard";
import PlayStart from "./pages/PlayStart";


function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<BigHeader/>}>
                    <Route path="/" element={<Dashboard/>}/>
                </Route>
                <Route element={<Master/>}>
                    <Route path="/play-start" element={<PlayStart/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
