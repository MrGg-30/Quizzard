import React from 'react';

function PlayWithFriend() {
    return (
        <div className="play-with-layout">
            <div className="left">
                <p className="title">
                    Get ready for some competition!
                </p>
                <img src="/media/play-with-friend.png" alt="img"/>
            </div>
            <div className="right">
                <div className="btns">
                    <a href="#" className="btn-dull">
                        Single Player
                    </a>
                    <a href="#" className="btn-full">
                        Multiple Player
                    </a>
                </div>

                <form>

                    <div className="select-cover">
                        <div className="select-option">
                            <span>Select Quiz Category</span>
                            <select placeholder="Category">
                                <option value="">Category</option>
                            </select>
                        </div>

                        <div className="select-option">
                            <span>Select Users From Friends</span>
                            <select placeholder="User">
                                <option value="">User</option>
                            </select>
                        </div>
                    </div>

                    <button type="submit">Send Challenge</button>
                </form>
            </div>
        </div>
    );
}

export default PlayWithFriend;