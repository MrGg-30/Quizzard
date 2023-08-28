import {Outlet} from "react-router-dom";
import DashboardHeader from "../components/DashboardHeader";

function BigHeader(props) {
    return <>
        <DashboardHeader/>
        <Outlet/>
    </>;
}

export default BigHeader;