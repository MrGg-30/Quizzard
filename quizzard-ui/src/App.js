import {BrowserRouter, Route, Routes} from "react-router-dom";
import BigHeader from "./layouts/BigHeader";
import Dashboard from "./pages/Dashboard";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<BigHeader/>}>
                    <Route path="/" element={<Dashboard/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
