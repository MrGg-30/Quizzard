import React from 'react';
import CountCards from "../components/CountCards";
import { useEffect, useState } from 'react';
import { Api } from '../api'

function Profile({ keycloak }) {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
        try {
            const fetchedUser = await Api.getUserByToken(keycloak.token); 
            setUser(fetchedUser.data);
        } catch (error) {
            console.error("Failed to fetch user:", error);
        }
        };

        fetchUser();
    }, []); 

    const handleProfilePicUpload = async (event) => {
        const file = event.target.files[0];

        if (file) {
            try {
              const formData = new FormData();
              formData.append("image", file);
      
              await Api.uploadUserProfilePic(user?.username, formData, keycloak?.token)
            
            } catch (error) {
              // Handle errors
              console.error("Image upload failed:", error);
            }
        } else {
            console.error("No image selected for upload.");
        }
    }

    return (
        <div className="profile-layout">
            <div className="profile-header">
                <img src={user?.profilePictureUrl?.includes('http') ? user?.profilePictureUrl : "/media/default-dp.png"} alt="quizzard user"/>
                <input type="file" accept="image/*" onChange={handleProfilePicUpload} />
                <p className="name">{user?.name || 'N/A'}</p>
                <a onClick={() => keycloak?.logout()} className="logout-btn"><i className="fa-solid fa-arrow-right-from-bracket"></i> Log Out</a>
            </div>
            <div className="count-cards-cover">
                <CountCards icon="/media/target.png" count={user?.quizCount || 0} name="quizzes"/>
                <CountCards icon="/media/fire.png" count={user?.wonQuizCount || 0} name="quizzes won"/>
                <CountCards icon="/media/coin.png" count={user?.totalPoints || 0} name="total points"/>
            </div>
            <div className="friends">
                <p className="title">Friends</p>
                <div className="user-node">
                    {user?.friends?.length ? (
                        user.friends.map(f => (
                            <>
                                <img src={f.image || "/media/default-dp.png"} alt="quizzard friend pic" />
                                <p>{f}</p>
                            </>
                        ))
                    ) : (
                        <p className='text-center w-100'>Oops no friends found!!!</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Profile;