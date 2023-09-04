import React, { useState, useEffect } from 'react';
import { Api } from '../api';  // Make sure the Api import is correct
import { useLocation } from 'react-router-dom';

function SinglePlayer({ keycloak, user }) {
  const location = useLocation();
  const { selectedCategory, selectedUser } = location.state || {};
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
    handleNextQuestion(answer === currentQuestion.correctAnswer);
  };

  return (
    <div className="play-start-layout">
      {isLoading ? (
        <div>Loading...</div>
      ) : !isQuizCompleted && questions.length > 0 ? (
        <div className="question">
          <div className="step-bar">
            <div className="bar"></div>
            <div className="steps">
              <span>{currentQuestionIndex + 1}</span>
            </div>
          </div>
          <div className="q-text">{currentQuestion.questionText}</div>

          {currentQuestion.possibleAnswers.map((answer, index) => (
            <div
              className={`op ${selectedAnswer === answer ? (answer === currentQuestion.correctAnswer ? 'selected-correct' : 'selected-wrong') : ''}`}
              key={index}
              onClick={() => handleAnswerClick(answer)}
            >
              {answer}
            </div>
          ))}
          <div className="counter">
         <div className="timer">
         {timeRemaining}
  </div>
</div>
        </div>
      ) : (
        <div className="quiz-completed">
            <h2>Quiz Completed!</h2>
            <p>Your Score: {score}</p>
        </div>
      )}
      

    </div>
  );
}

export default SinglePlayer;
