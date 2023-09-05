import React from 'react';
import '../scss/notificationCard.scss';
import { config } from '../Constants';

function Notification({ onAccept, onReject, senderUsername, category }) {
  return (
    <div className="notification-header">
      <div className='div-header'>
        <div>
          <p className='text'>QUIZ REQUEST</p>
        </div>
      </div>

      <div className='grid-wraper'>
        <div className="grid-container">
          <div className="column1">
          <img src={`${config.url.S3_BUCKET_URL}/${senderUsername}-profile-picture` || "/media/default-dp.png"}  />
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
