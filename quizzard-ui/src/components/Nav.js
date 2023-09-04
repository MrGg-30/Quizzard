import { useNavigate } from 'react-router-dom'
import { routes } from '../routes';

function Nav() {
    const navigate = useNavigate();

    return (
        <div className="master-nav">
            <div className="logo">
                <img src="/media/baba-logo.svg" alt="Logo" onClick={() => navigate(routes.main)}/>
                <span>quizzard</span>
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

                <a className="link cursor-pointer" onClick={() => navigate(routes.friends)}>
                    <i className="fa-solid fa-user-group"></i>
                    <span>Friends</span>
                </a>

                <a className="auth-dp cursor-pointer" onClick={() => navigate(routes.userProfile)}>
                    <img src="/media/default-dp.png" alt="dp"/>
                </a>
            </div>
        </div>
    );
}

export default Nav;