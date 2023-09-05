import React from 'react';
import CountCards from "../components/CountCards";
import { useEffect, useState } from 'react';
import { config } from '../Constants';
import { Api } from '../api'

function Profile({ keycloak, user }) {
    // const [user, setUser] = useState(null);

    // useEffect(() => {

    //     const fetchUser = async () => {
    //     try {
    //         const fetchedUser = await Api.getUserByToken(keycloak.token); 
    //         setUser(fetchedUser.data);
    //     } catch (error) {
    //         console.error("Failed to fetch user:", error);
    //     }
    //     };

    //     const timer = setTimeout(() => {
    //         fetchUser();
    //     }, 2000);  // For 1 second. Change to 2000 for 2 seconds.

    //     // Cleanup function to clear the timer
    //     return () => {
    //         clearTimeout(timer);
    //     };
    // }, []); 

    const handleProfilePicUpload = async (event) => {
        const file = event.target.files[0];

        if (file) {
            try {
              const formData = new FormData();
              formData.append("file", file);
      
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
                <img src={user?.profilePictureUrl?.includes('profile') ? `${config.url.S3_BUCKET_URL}/${user?.profilePictureUrl}` : "/media/default-dp.png"} alt="quizzard user"/>
                <input type="file" accept="image/*" onChange={handleProfilePicUpload} />
                <p className="name">{user?.name || 'N/A'}</p>
                <a onClick={() => keycloak?.logout()} className="logout-btn"><i className="fa-solid fa-arrow-right-from-bracket"></i> Log Out</a>
            </div>
            <div className="count-cards-cover">
                <CountCards icon="/media/target.png" count={user?.quizCount/2 || 0} name="quizzes"/>
                <CountCards icon="/media/fire.png" count={user?.wonQuizCount || 0} name="quizzes won"/>
                <CountCards icon="/media/coin.png" count={user?.totalPoints/2 || 0} name="total points"/>
            </div>
            <div className="friends">
                <div className="friends">
                    <p className="title">Friends</p>
            
                        {user?.friends?.length ? (
                            user.friends.map(f => (
                                <div className="user-node">
                                    <img src={`${config.url.S3_BUCKET_URL}/${f}-profile-picture` || "/media/default-dp.png"} alt="quizzard friend pic" />
                                    <p>{f}</p>
                                </div>
                            ))
                        ) : (
                            <p className='text-center w-100'>No Friends Yet</p>
                        )}
                    
                </div>
            </div>
        </div>
    );
}

export default Profile;