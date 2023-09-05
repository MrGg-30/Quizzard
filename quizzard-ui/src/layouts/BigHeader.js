import { Outlet } from "react-router-dom";
import DashboardHeader from "../components/DashboardHeader";

function BigHeader({ keycloak }) {
    return <>
        <DashboardHeader keycloak={keycloak} />
        <Outlet/>
    </>;
}

export default BigHeader;