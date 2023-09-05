import React from 'react';
import '../scss/notificationCard.scss';

function Notification({ onAccept, onReject, senderUsername, category }) {
  return (
    <div className="header">
      <div className='div-header'>
        <div>
          <p className='text'>QUIZ REQUEST</p>
        </div>
        <div>
          <img src="/bellIcone.png" alt="Icon" />
        </div>
      </div>

      <div className='grid-wraper'>
        <div className="grid-container">
          <div className="column1">
            <img
              src="/userImage.png"
              alt="Avatar"
            />
          </div>
          <div className="column2">
            <div className="row1">{senderUsername}</div>
            <div className="row2">Wants to Play with you</div>
            <div className="row3">
              <div>
                <p className='category-name'>{category}</p>
              </div>
              <div className='button-div'>
                <div className="button1" onClick={onAccept}>Accept</div>
                <div className="button2" onClick={onReject}>Reject</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Notification;
