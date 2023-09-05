
import React, { useState, useEffect } from 'react';
import { Api } from '../api';  // Make sure the Api import is correct
import { useLocation } from 'react-router-dom';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { routes } from '../routes';
import { useNavigate } from 'react-router-dom';
import 'semantic-ui-css/semantic.min.css';
import {
  CircularProgressbar,
  CircularProgressbarWithChildren,
  buildStyles
} from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import RadialSeparators from "../components/RadialSeparators";


function MultiPlayer({ keycloak, user }) {
    
  const [questions, setQuestions] = useState([]);
  const navigate = useNavigate();
  const [client, setClient] = useState(null);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [score, setScore] = useState(0);
  const [friendsScore, setFriendsScore] = useState(null);
  const [isQuizCompleted, setIsQuizCompleted] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [timeRemaining, setTimeRemaining] = useState(20); 
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const selectedCategory = query.get("category");
  const anotherUser = query.get("anotherUser");
  const getAndSetQuestions = async () => {
    try {
      if (selectedCategory) {
        const response = await Api.getQuizQuestionsByCategory(keycloak.token, selectedCategory);

        if (response.status === 200) {
          setQuestions(response.data.questions);
          setIsLoading(false);
        }
      }
    } catch (error) {
      console.error("An error occurred:", error);
    }
  };

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
      }

      var path3 = '/user/' + user.username + '/game-result'
      newClient.subscribe(path3, (message) => {
        const response = JSON.parse(message.body);
        if (response.anotherUsername === user.username) {
          console.log("Game result of another user received:", response);
          console.log("Sxvisi Qula: " + response.score)
          setFriendsScore(response.score)
          console.log("Chemi Qula: " + score)
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

  useEffect(() => {
    if (keycloak?.token) {
      getAndSetQuestions();
    }
  }, []);

  useEffect(() => {
    if (timeRemaining > 0 && !isQuizCompleted) {
      const timer = setTimeout(() => {
        setTimeRemaining(timeRemaining - 1);
      }, 1000);

      return () => {
        clearTimeout(timer);
      };
    } else if (timeRemaining === 0) {
      handleNextQuestion(false); 
    }
  }, [timeRemaining, isQuizCompleted]);

  useEffect(() => {
    console.log("Debug: friendsScore", friendsScore); 
    if (client && isQuizCompleted) {
        const gameResult = {
            category: selectedCategory,
            currentUsername: user.username,
            anotherUsername: anotherUser,
            score: score
          };
        client.publish({
            destination: '/app/game-result',
            body: JSON.stringify(gameResult),
          });
    }
    
    
  }, [client, isQuizCompleted]);

  useEffect(() => {
    if (isQuizCompleted && friendsScore) {
        console.log("Navigation to other page!!!!!")
        navigate(`${routes.gameResults}?score=${score}&friendsScore=${friendsScore}&friendsName=${anotherUser}`);
    }
  }, [isQuizCompleted, friendsScore, navigate]);

  const handleNextQuestion = (isCorrect) => {
    if (isCorrect) {
      setScore(score + 1);
    }

    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex(currentQuestionIndex + 1);
    } else {
      setIsQuizCompleted(true);
    }

    setSelectedAnswer(null);
    setTimeRemaining(20); 
  };

  const currentQuestion = questions[currentQuestionIndex];

  const handleAnswerClick = (answer) => {
    setSelectedAnswer(answer);
    setTimeout(function () {
      handleNextQuestion(answer === currentQuestion.correctAnswer);
    }, 1000);
  };

  const colors = ['#9EA1D4', '#FFCBCBDD', '#A8D1D1', '#FD8A8A']

  return (
    <div className="play-start-layout">
      {isLoading ? (
        <div>Loading...</div>
      ) : !isQuizCompleted && questions.length > 0 ? (
        <div className="question">
          <div className="step-bar">
            <div className="bar">
              <div className="completed-bar" style={{ width: `${(currentQuestionIndex / questions?.length) * 100}%` }}></div>
            </div>
            <div className="steps">
              {Array.from({ length: questions?.length }, (_, index) => (
                <p
                  key={index}
                  className={index < currentQuestionIndex ? 'completed-step' : 'incomplete-step'}
                >
                  {index + 1}
                </p>
              ))}
            </div>
          </div>
          <div className="q-text">{currentQuestion.questionText}</div>

          {currentQuestion.possibleAnswers.map((answer, index) => (
            <div
              className={`op ${selectedAnswer === currentQuestion?.correctAnswer ? 'selected-correct' : 'selected-wrong'}`}
              key={answer}
              style={{
                backgroundColor: colors[index],
                color: 'black',
                backgroundColor:
                  selectedAnswer ? selectedAnswer === answer
                    ? selectedAnswer === currentQuestion.correctAnswer
                      ? 'green'
                      : 'red'
                    : "#D9D9D9"
                    : colors[index]
              }} onClick={() => handleAnswerClick(answer)}
            >
              {answer}
            </div>
          ))}
        </div>
      ) : (
        <div className="quiz-completed">
          <h2>Quiz Completed!</h2>
          <p>Waiting for your opponent to finish!</p>
        </div>
      )}

      {!isQuizCompleted && questions.length > 0 && <div style={{ width: 150, height: 150 }}>
        <CircularProgressbarWithChildren
          value={timeRemaining / 20 * 100}
          text={timeRemaining}
          strokeWidth={5}
          styles={buildStyles({
            strokeLinecap: "butt",
          })}
        >
          <RadialSeparators
            count={20}
            style={{
              background: "#fff",
              width: "2px",
              // This needs to be equal to props.strokeWidth
              height: `${5}%`
            }}
          />
        </CircularProgressbarWithChildren>
      </div>}

    </div>
  );
}

export default MultiPlayer;
