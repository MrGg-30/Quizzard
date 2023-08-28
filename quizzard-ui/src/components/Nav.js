import React from 'react';

function Nav(props) {
    return (
        <div className="master-nav">
            <div className="logo">
                <img src="/media/baba-logo.svg" alt="Logo"/>
                <span>quizzard</span>
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
                    <span>Leaderboard</span>
                </a>

                <a className="link" href="#">
                    <i className="fa-solid fa-user-group"></i>
                    <span>Friends</span>
                </a>

                <a className="auth-dp" href="">
                    <img src="/media/default-dp.png" alt="dp"/>
                </a>
            </div>
        </div>
    );
}

export default Nav;