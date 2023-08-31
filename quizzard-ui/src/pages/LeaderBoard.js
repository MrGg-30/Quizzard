import React from 'react';

function LeaderBoard(props) {
    return (
        <div className="leader-board">
            <p className="title">
                Top Performers in <b>Math</b>
            </p>
            <div className="left">

                <div className="position-bars">
                    <div className="p2">2
                        <div className="badge">
                            <img className="dp" src="/media/default-dp.png" alt="dp"/>
                            <img className="m" src="/media/p2.png" alt="img"/>
                        </div>
                    </div>
                    <div className="p1">1
                        <div className="badge">
                            <img className="dp" src="/media/default-dp.png" alt="dp"/>
                            <img className="m" src="/media/p1.png" alt="img"/>
                        </div>
                    </div>
                    <div className="p3">3
                        <div className="badge">
                            <img className="dp" src="/media/default-dp.png" alt="dp"/>
                            <img className="m" src="/media/p3.png" alt="img"/>
                        </div>
                    </div>
                </div>


            </div>
            <div className="right">
                <div className="point-list">
                    <div className="point">
                        <div className="medal">
                            <img src="/media/medal.png" alt=""/>
                            <span>4</span>
                        </div>
                        <p className="name">Nick</p>
                        <span className="scores">450 Pt</span>
                    </div>
                    <div className="point">
                        <div className="medal">
                            <img src="/media/medal.png" alt=""/>
                            <span>4</span>
                        </div>
                        <p className="name">Nick</p>
                        <span className="scores">450 Pt</span>
                    </div>
                    <div className="point">
                        <div className="medal">
                            <img src="/media/medal.png" alt=""/>
                            <span>4</span>
                        </div>
                        <p className="name">Nick</p>
                        <span className="scores">450 Pt</span>
                    </div>
                    <div className="point">
                        <div className="medal">
                            <img src="/media/medal.png" alt=""/>
                            <span>4</span>
                        </div>
                        <p className="name">Nick</p>
                        <span className="scores">450 Pt</span>
                    </div>
                    <div className="point">
                        <div className="medal">
                            <img src="/media/medal.png" alt=""/>
                            <span>4</span>
                        </div>
                        <p className="name">Nick</p>
                        <span className="scores">450 Pt</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LeaderBoard;