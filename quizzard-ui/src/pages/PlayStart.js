import React from 'react';

function PlayStart() {
    return (
        <div className="play-start-layout">
            <div className="question">
                <div className="step-bar">
                    <div className="bar"></div>
                    <div className="steps">
                        <span>1</span>
                        <span>2</span>
                        <span>3</span>
                        <span>4</span>
                        <span>5</span>
                    </div>
                </div>

                <div className="q-text">Test Question Title!!!!</div>

                <div className="op">Option 1</div>
                <div className="op">Option 2</div>
                <div className="op">Option 3</div>
                <div className="op">Option 4</div>
            </div>

            <div className="counter">
                {/*<div className="progress-bar">*/}
                {/*    <progress value="43" min="0" max="100" style={{visibility: 'hidden', height: 0, width: 0}}>75%*/}
                {/*    </progress>*/}
                {/*</div>*/}

                <div className="friend-photo">
                    <p>You Are Playing With</p>
                    <img src="/media/default-dp.png" alt="img"/>
                    <p>Test User</p>
                </div>

                <img src="/media/timer.png" alt="img"/>

            </div>
        </div>
    );
}

export default PlayStart;