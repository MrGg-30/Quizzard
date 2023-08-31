import React from 'react';

function GameResults() {
    return (
        <div className="game-result-layout">
            <p className="title">
                hats off to our <br/> brilliant winner!
            </p>

            <div className="players">
                <div className="me">
                    <img className="top" src="/media/crown.png" alt="taag"/>
                    <img className="dp b-green" src="/media/default-dp.png" alt=""/>
                    <span>me</span>
                </div>
                <div className="points">
                    <span className="green">130</span> - <span className="red">110</span>
                </div>
                <div className="friend">
                    <img className="top" src="/media/hat.png" alt="hat"/>
                    <img className="dp b-red" src="/media/default-dp.png" alt=""/>
                    <span>Test User</span>
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