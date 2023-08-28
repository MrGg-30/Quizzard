import React from 'react';

function SimpleCard({title, photo}) {
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
            </div>
            <a href="#" className="card-btn">
                Play Now
            </a>
        </div>
    );
}

export default SimpleCard;