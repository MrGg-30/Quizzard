import { Link, useNavigate } from 'react-router-dom'
import { routes } from '../routes';

import '../scss/app.scss';

function DashboardHeader({ keycloak }) {
    const navigate = useNavigate();

    return (
        <div className="dashboard-header">
            <div className="transparent-nav">

                <div className="logo" onClick={() => navigate(routes.main)}>
                    <img src="media/baba-logo.svg" alt="Logo"/>
                    <p>QUIZZARD</p>
                </div>

                <div className="icons">
                    <a className="link cursor-pointer" onClick={() => navigate(routes.playWithFriends)}>
                        <i className="fa-solid fa-gamepad"></i>
                        <span>Play</span>
                    </a>

                    <a className="link cursor-pointer" onClick={() => navigate(routes.createQuiz)}>
                        <i className="fa-regular fa-circle-question"></i>
                        <span>Create Quiz</span>
                    </a>

                    <a className="link cursor-pointer" onClick={() => navigate(routes.leaderBoard)}>
                        <i className="fa-solid fa-medal"></i>
                        <span>Leaderboard</span>
                    </a>

                    <Link className="nav-btn" to={routes.userProfile}>
                        Login
                    </Link>
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