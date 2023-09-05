import React from 'react';
import SimpleCard from "../components/SimpleCard";

function Dashboard(props) {
    return (
        <div className="dashboard-show-case">
            <p className="title">Featured Quizzes</p>
            <div className="cards-cover">
                <SimpleCard title="Discrete Mathematics" photo="/media/math.jpg"/>
                <SimpleCard title="Physics" photo="/media/physics.jpg"/>
                <SimpleCard title="Science" photo="/media/science.jpg"/>
            </div>
        </div>
    );
}

export default Dashboard;