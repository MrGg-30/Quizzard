import React from 'react';

function Friends() {
    return (
        <div className="friends-layout">
            <div className="friends">
                <p className="title">Friends</p>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>
                </div>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>

                </div>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>

                </div>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>

                </div>
            </div>
            <div className="send-request">
                <p className="title">
                    Send Friend Requests
                </p>
                <form action="#">
                    <input type="text" placeholder="Quizzard"/>
                </form>
                <img src="/media/friends.png" alt="poster"/>
            </div>
            <div className="get-requests">
                <p className="title">Friend Requests</p>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>
                    <div className="icons">
                        <i className="fa fa-check"></i>
                        <i className="fa fa-times"></i>
                    </div>
                </div>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>
                    <div className="icons">
                        <i className="fa fa-check"></i>
                        <i className="fa fa-times"></i>
                    </div>
                </div>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>
                    <div className="icons">
                        <i className="fa fa-check"></i>
                        <i className="fa fa-times"></i>
                    </div>
                </div>
                <div className="user-node">
                    <img src="/media/default-dp.png" alt="dp"/>
                    <p>Nick</p>
                    <div className="icons">
                        <i className="fa fa-check"></i>
                        <i className="fa fa-times"></i>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Friends;