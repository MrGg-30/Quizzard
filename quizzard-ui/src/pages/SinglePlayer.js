import React, { useState, useEffect } from 'react';
import { Api } from '../api';  // Make sure the Api import is correct
import { useLocation } from 'react-router-dom';
import 'semantic-ui-css/semantic.min.css';
import {
  CircularProgressbarWithChildren,
  buildStyles
} from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import RadialSeparators from "../components/RadialSeparators";

function SinglePlayer({ keycloak, user }) {
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const selectedCategory = query.get("category");
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [score, setScore] = useState(0);
  const [isQuizCompleted, setIsQuizCompleted] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [timeRemaining, setTimeRemaining] = useState(20);
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

  const handleNextQuestion = async (isCorrect) => {
    if (isCorrect) {
      setScore((prevScore) => prevScore + 1);
    }

    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex(currentQuestionIndex + 1);
    } else {
      const data = {
        category: selectedCategory,
        playerScores: {
          username: user?.username,
          score: score,
        }
      }
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
                backgroundColor: (() => {
                  if (selectedAnswer) {
                      if (selectedAnswer === answer) {
                          return selectedAnswer === currentQuestion.correctAnswer
                          ? 'green'
                          : 'red';
                      } else if (answer === currentQuestion.correctAnswer) {
                          return 'green';
                      } else {
                          return '#D9D9D9'; // gray color for unselected options
                      }
                  }
                  return colors[index];
              })(),
              color: 'black'
              }} onClick={() => handleAnswerClick(answer)}
            >
              {answer}
            </div>
          ))}
        </div>
      ) : (
        <div className="quiz-completed">
          <h2>Quiz Completed!</h2>
          <p>Your Score: {score}</p>
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

export default SinglePlayer;
