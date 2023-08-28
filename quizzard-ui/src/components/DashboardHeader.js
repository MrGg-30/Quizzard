import {compareArraysAsSet} from "@testing-library/jest-dom/dist/utils";

function DashboardHeader() {
    return (
        <div className="dashboard-header">
            <div className="transparent-nav">

                <div className="logo">
                    <img src="media/baba-logo.svg" alt="Logo"/>
                    <p>QUIZZARD</p>
                </div>

                <div className="icons">
                    <a className="link" href="#">
                        <i className="fa-solid fa-gamepad"></i>
                        <span>Play</span>
                    </a>

                    <a className="link" href="#">
                        <i className="fa-regular fa-circle-question"></i>
                        <span>Create Quiz</span>
                    </a>

                    <a className="link" href="#">
                        <i className="fa-solid fa-medal"></i>
                        Leaderboard
                    </a>

                    <a className="nav-btn" href="#">
                        SIGN UP
                    </a>

                </div>
            </div>
            <div className="dis">
                <p>unleash Your</p>
                <p>inner <span>wizard</span> of</p>
                <p>wisdom</p>
            </div>
        </div>
    );
}

export default DashboardHeader;