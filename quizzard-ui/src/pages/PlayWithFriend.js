import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { routes } from '../routes';
import { Api } from '../api'
import Select from 'react-select';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import NotificationModal from '../components/NotificationModal';
import { setMultiGlobalQuestions } from '../global';

function PlayWithFriend({ keycloak }) {
  const navigate = useNavigate();
  const [singlePlayerClicked, setSinglePlayerClicked] = useState(false);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedFriend, setSelectedFriend] = useState('');
  const [client, setClient] = useState(null);
  const [gameRequestReceived, setGameRequestReceived] = useState(null);
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

  useEffect(() => {
    const newClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8081/ws'),
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
    });

    newClient.onConnect = (frame) => {
      console.log('Connected: ' + frame);

      // Stomp headers for authentication
      const headers = {
        'Authorization': `Bearer ${keycloak.token}`
      };

      var path = '/user/' + user.username + '/game-request'
      newClient.subscribe(path, (message) => {
        const request = JSON.parse(message.body);
        if (request.to === user.username) {
          setGameRequestReceived(request);
        }
      }, headers);
      
      // Subscribe to a topic to receive game responses
      var path2 = '/user/' + user.username + '/game-response'
      newClient.subscribe(path2, (message) => {
        const response = JSON.parse(message.body);
        if (response.from === user.username) {
          console.log("Game response received:", response);
          if (response.status == 'ACCEPTED') {
            // fetch questions
            var questions = response.questions
            setMultiGlobalQuestions(questions)
            navigate(`${routes.multiPlayer}?category=${response.category}&anotherUser=${response.to}`);
          }
        }
      }, headers);
    };

    

    newClient.activate();
    setClient(newClient);

    return () => {
      if (newClient.connected) {
        newClient.deactivate();
      }
    };
  }, [keycloak]);

  const getAndSetCategories = async () => {
    try {
      const response = await Api.fetchQuizCategoriesWithoutParams(keycloak.token);
      if (response.status === 200) {
        setCategories(response.data);
      }
    } catch (error) {
      console.error("An error occurred:", error);
    }
  };

  useEffect(() => {
    if (keycloak?.token) {
        getAndSetCategories();
    }
  }, [keycloak]);

  const options = categories?.map(c => ({ value: c, label: c }));
  const friendOptions = user?.friends?.map(c => ({ value: c, label: c }));

  const handleSinglePlayerClick = () => {
    setSinglePlayerClicked(true);
  };

  const handleMultiPlayerClick = () => {
    setSinglePlayerClicked(false);
  };

  const handleSendChallengeClick = () => {
    if (singlePlayerClicked) {
        const state = {
            selectedCategory,
            selectedUser: user,
          };
          console.log(state)
          navigate(routes.singlePlayer, { state });
    } else if (client && client.connected && selectedFriend && selectedCategory) {
      const gameRequest = {
        from: user.username,
        to: selectedFriend,
        category: selectedCategory,
      };
      console.log(gameRequest)
      client.publish({
        destination: '/app/game-request',
        body: JSON.stringify(gameRequest),
      });
    }

  };

  const handleCategoryChange = selectedOption => {
    setSelectedCategory(selectedOption.value);
  };

  const handleFriendChange = selectedOption => {
    setSelectedFriend(selectedOption.value);
  };


  const handleSendResponseClick = async (responseStatus) => {
    if (gameRequestReceived && client && client.connected) {
      var category = gameRequestReceived.category

      
       // Reset game request state
       if (responseStatus == 'ACCEPTED') {
        const response = await Api.getQuizQuestionsByCategory(keycloak.token, category);  

        if (response.status === 200) {
          const gameResponse = {
            ...gameRequestReceived,
            status: responseStatus,
            questions: response.data.questions
          };
          client.publish({
            destination: '/app/game-response',
            body: JSON.stringify(gameResponse),
          });

          setMultiGlobalQuestions(response.data.questions);
        }
        console.log("accepted quiz and will start to play soon!")

        navigate(`${routes.multiPlayer}?category=${category}&anotherUser=${gameRequestReceived.from}`);
       }
       setGameRequestReceived(null); 
    }
  };

  return (
    <div className="play-with-layout">
      <div className="left">
        <p className="title">Get ready for some competition!</p>
        <img src="/media/play-with-friend.png" alt="img" />
      </div>
      <div className="right">
        <div className="btns">
        <a href="#" className={singlePlayerClicked ? 'btn-full' : "btn-dull"} onClick={handleSinglePlayerClick}>
            Single Player
        </a>
        <a href="#" className={!singlePlayerClicked ? 'btn-full' : "btn-dull"} onClick={handleMultiPlayerClick}>
            Multiple Player
        </a>
        </div>
        <form onSubmit={e => { e.preventDefault(); handleSendChallengeClick(); }}>
          <div className="select-cover">
            <div className="select-option">
              <span>Select Quiz Category</span>
              <Select placeholder="Category" options={options} onChange={handleCategoryChange} />
            </div>
            {!singlePlayerClicked && (
              <div className="select-option">
                <span>Select Friend</span>
                <Select placeholder="Friend" options={friendOptions} onChange={handleFriendChange} />
              </div>
            )}
          </div>
          <button type="submit">Play</button>
        </form>
      </div>
      {gameRequestReceived && (
        <NotificationModal
        senderUsername={gameRequestReceived.from}
        category={gameRequestReceived.category}
        isOpen={Boolean(gameRequestReceived)}
        onAccept={() => handleSendResponseClick('ACCEPTED')}
        onReject={() => handleSendResponseClick('DECLINED')}
      />
      )}
    </div>
  );
}

export default PlayWithFriend;
