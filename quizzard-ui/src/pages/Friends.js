import { useState, useEffect } from 'react';
import { Loader } from 'semantic-ui-react';
import { config } from '../Constants';
import { Api } from '../api'
import '../scss/friends.scss'

function Friends({ keycloak, user }) {
    const [pendingRequests, setPendingRequests] = useState([]);
    const [searchedUsers, setSearchedUser] = useState([]);
    const [searchPrefix, setSearchPrefix] = useState("");
    const [isLoading, setLoading] = useState(false);

    const getAndSetPendingRequests = async () => {
        try {
            const response = await Api.getPendingRequestsByUsername(keycloak?.token, user?.username);

            if (response.status === 200) {
                setPendingRequests(response.data);
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    }
    const getAndSetSearchUser = async () => {
        setLoading(true)
        try {
            if (searchPrefix) {
                const response = await Api.getUserbySearchPrefix(keycloak?.token, searchPrefix);
                if (response.status === 200) {
                    setLoading(false)
                    const excludedUsers = response?.data?.filter((currUser) =>
                        !pendingRequests?.some((pendReq) => pendReq.from === currUser.username) && currUser.username !== user.username && !user.friends.includes(currUser.username)
                    );

                    setSearchedUser(excludedUsers)
                }
            } else {
                setLoading(false)
                setSearchedUser([])
            }

        } catch (error) {
            setLoading(false)
            console.error("An error occurred:", error);
        }
    }

    const getUserPicture = async (username) => {
        try {
            const response = await Api.getUserPictureByUsername(keycloak?.token, username);
            if (response.status === 200) {
                return response?.data
            } else {
                setSearchedUser([])
            }
        } catch (error) {
            console.error("An error occurred:", error);
            setSearchedUser([])
        }
    }
    const postFriendRequest = async (data) => {
        try {
            const response = await Api.sendFriendRequest(keycloak?.token, data);
            if (response.status === 200) {
                return true
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    }
    const acceptOrDenyRequest = async (data) => {
        try {
            const response = await Api.updateFriendRequest(keycloak?.token, data);
            console.log(response, "acceptOrDenyRequest")
            if (response.status === 200) {
                return true
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    }

    useEffect(() => {
        if (keycloak?.token && user?.username) {
            getAndSetPendingRequests()
        }
    }, [keycloak?.token, user?.username])

    useEffect(() => {
        if (keycloak?.token && user?.username && searchPrefix) {
            getAndSetSearchUser()
        } else {
            setSearchPrefix([])
        }
    }, [keycloak?.token, user?.username, searchPrefix])

    useEffect(() => {
        const excludedUsers = searchedUsers?.filter((user) =>
            !pendingRequests.some((pendReq) => pendReq.from === user.username)
        );

        setSearchedUser(excludedUsers)
    }, [])

    const handleSearch = (e) => {
        setSearchPrefix(e.target.value)
        if (!e.target.value)
            setSearchedUser()
    }

    const handleSendRequest = async (reqTo) => {
        const data = {
            from: user?.username,
            to: reqTo,
            status: "PENDING"
        }
        const res = await postFriendRequest(data)
        if (res) {
            const updatedData = searchedUsers?.filter((item) => item.username !== reqTo);
          setSearchedUser(updatedData)
        }
    }
    const handleAcceptRequest = async (reqTo) => {
        const data = {
            from: user?.username,
            to: reqTo,
            status: "ACCEPTED"
        }
        const res = await acceptOrDenyRequest(data)
        if (res) {
            getAndSetPendingRequests()
        }
    }
    const handleDeclineRequest = async (reqTo) => {
        const data = {
            from: user?.username,
            to: reqTo,
            status: "DECLINED"
        }
        const res = await acceptOrDenyRequest(data)
        if (res) {
            getAndSetPendingRequests()
        }
    }

    return (
        <div className="friends-layout">
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
                    <p className='text-center w-100'>Oops no friends found!!!</p>
                )
                }
            </div>
            <div className="send-request">
                <p className="title">
                    Send Friend Requests
                </p>
                <form action="#">
                    <input onChange={handleSearch} value={searchPrefix} type="text" placeholder="Quizzard" />
                </form>
                <div className='main-container'>
                    {isLoading && <Loader active inline="centered">
                        Loading...
                    </Loader>}
                    {searchedUsers?.length ? (
                        searchedUsers.map(f => (
                            
                            <div className="container">
                                {console.log(f)}
                                <div className="image-container">
                                    {/* <img src="imageUrl" className="image" /> */}
                                </div>
                                <div >
                                    {f?.username}
                                </div>
                                <button onClick={() => handleSendRequest(f?.username)} className="send-button">Send</button>
                            </div>
                        ))
                    ) : (
                        <img src="/media/friends.png" alt="poster" />
                    )
                    }
                </div>

            </div>
            <div className="get-requests">
                <p className="title">Friend Requests</p>
                {pendingRequests?.length ? (
                    pendingRequests.map(f => (
                        <div className="user-node">
                            <img src={`${config.url.S3_BUCKET_URL}/${f}-profile-picture` || "/media/default-dp.png"} alt="quizzard friend pic" />
                            <p>{f.from}</p>
                            <div className="icons" style={{ display: 'flex', gap: 15 }}>
                                <i onClick={() => handleAcceptRequest(f.from)} style={{ cursor: "pointer" }} className="fa fa-check"></i>
                                <i onClick={() => handleDeclineRequest(f.from)} style={{ cursor: "pointer" }} className="fa fa-times"></i>
                            </div>
                        </div>
                    ))
                ) : (
                    <p className='text-center w-100'>No pending friend requests</p>
                )
                }
            </div>
        </div>
    );
}

export default Friends;