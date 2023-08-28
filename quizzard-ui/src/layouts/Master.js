import {Outlet} from 'react-router-dom';
import Nav from "../components/Nav";

function Master(props) {
    return <>
        <Nav/>
        <Outlet/>
    </>;
}

export default Master;