import React from 'react';

function CountCards({icon, count, name}) {
    return (
        <div className="count-cards">
            <img src={icon} alt="img"/>
            <div className="info">
                <p className="count">{count}</p>
                <p className="name">{name}</p>
            </div>
        </div>
    );
}

export default CountCards;