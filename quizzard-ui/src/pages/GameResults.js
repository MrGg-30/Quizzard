import React from 'react';
import { useLocation } from 'react-router-dom';
import { config } from '../Constants';


function GameResults({ keycloak, user }) {
    const location = useLocation();
    const query = new URLSearchParams(location.search);
    const score = query.get("score");
    const friendsScore = query.get("friendsScore");
    const friendName = query.get("friendsName");

  return (
    <div className="game-result-layout">
      <p className="title">Hats off to our <br/> brilliant winner!</p>
      <div className="players">
        <div className="me">
          <img className="top" src="/media/crown.png" alt="crown"/>
          <img className="dp b-green" src={`${config.url.S3_BUCKET_URL}/${user.username}-profile-picture`} alt="avatar"/>
          <span>{user.username}</span>
        </div>
        <div className="points">
          <span className="green">{score}</span> - <span className="red">{friendsScore}</span>
        </div>
        <div className="friend">
          <img className="top" src="/media/hat.png" alt="hat"/>
          <img className="dp b-red" src={`${config.url.S3_BUCKET_URL}/${friendName}-profile-picture`} alt="avatar"/>
          <span>{friendName}</span>
        </div>
      </div>
      <div className="pics">
        <img src="/media/game-rel2.png" alt="img"/>
        <a href="#">Home</a>
        <img src="/media/game-rel1.png" alt="img"/>
      </div>
    </div>
  );
}

export default GameResults;