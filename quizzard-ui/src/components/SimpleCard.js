import React from 'react';
import { routes } from '../routes';
import { useNavigate } from 'react-router-dom';


function SimpleCard({title, photo}) {
    const navigate = useNavigate();

    const handleStartPlaying = () => {
        navigate(`${routes.singlePlayer}?category=${title}`);
      };
      
    return (
        <div className="simple-card">
            <img src={photo} alt=""/>
            <p className="title">{title}</p>
            <div className="rate-row">
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star"></i>
                <i className="fa-solid fa-star"></i>
                <i className="fa-regular fa-star"></i>
                <span>(difficulty)</span>
            </div>
            <a href="#" className="card-btn" onClick={handleStartPlaying}>
                Play Now
            </a>
        </div>
    );
}

export default SimpleCard;