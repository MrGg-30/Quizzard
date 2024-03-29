import React from 'react';
import { routes } from '../routes';
import { useNavigate } from 'react-router-dom';

import { useLocation } from 'react-router-dom';
import { config } from '../Constants';


function GameResultsTie({ keycloak, user }) {
    const location = useLocation();
    const navigate = useNavigate();
    const query = new URLSearchParams(location.search);
    const score = query.get("score");
    const friendsScore = query.get("friendsScore");
    const friendName = query.get("friendsName");

  return (
    <div className="game-result-layout">
      <p className="title">You have quite similar <br/> Knowledge!</p>
      <div className="players">
        <div className="me">
        <img className="top" src="/media/hat.png" alt="hat"/>
          <img className="dp b-green" src={`${config.url.S3_BUCKET_URL}/${user.username}-profile-picture`} alt="avatar"/>
          <span>{user.username}</span>
        </div>
        <div className="points">
          <span className="green">{score}</span> - <span className="green">{friendsScore}</span>
        </div>
        <div className="friend">
          <img className="top" src="/media/hat.png" alt="hat"/>
          <img className="dp b-green" src={`${config.url.S3_BUCKET_URL}/${friendName}-profile-picture`} alt="avatar"/>
          <span>{friendName}</span>
        </div>
      </div>
      <div className="pics">
        <a href="#" onClick={() => navigate(routes.main)}>Home</a>
      </div>
    </div>
  );
}

export default GameResultsTie;