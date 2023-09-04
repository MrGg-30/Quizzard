import Select from 'react-select'
import { useState, useEffect } from 'react'

import { Api } from '../api'
import Img from '../assets/images/create-quiz.png'
import '../scss/createquiz.scss'

const CreateQuiz = ({keycloak}) => {
    const [categories, setCategories] = useState([])
    const [selectedCategory, setSelectedCategory] = useState('')
    const [enteredCategory, setEnteredCategory] = useState('')
    const [stepCount, setStepCount] = useState(1);
    const [newQuestion, setNewQuestion] = useState('')
    const [newQuestionOptions, setNewQuestionOptions] = useState({
        optionOne: '',
        optionTwo: '',
        optionThree: '',
        optionFour: ''
    })
    const [correctAnswer, setCorrectAnswer] = useState()
    
    // const options = categories?.map(c => ({ value: c, label: c }))
    // const getAndSetCategories = async () => {
    //     try {
    //         const response = await Api.fetchQuizCategoriesWithoutParams(keycloak.token);

    //         if (response.status === 200) {
    //             setCategories(response.data);
    //         }
    //     } catch (error) {
    //         console.error("An error occurred:", error);
    //     }
    // }

    useEffect(() => {
        if(keycloak?.token) {
        //   getAndSetCategories()
        }
    }, [keycloak?.token])

    // const handleCategoryChange = (cat) => {
    //     setSelectedCategory(cat?.value);
    // }

    // {
    //     "category": "string",
    //     "questionText": "string",
    //     "correctAnswer": "string",
    //     "possibleAnswers": [
    //       "string"
    //     ]
    // }

    const handleQuestionAdd = async () => {
        try {
            if(!correctAnswer) {
                alert('Please select correct answer!')
                return
            }
            const newQuiz = {
                category: enteredCategory,
                questionText: newQuestion,
                correctAnswer,
                possibleAnswers: [newQuestionOptions.optionOne, newQuestionOptions.optionTwo, newQuestionOptions.optionThree, newQuestionOptions.optionFour]
            }
            const response = await Api.createQuiz(keycloak.token, newQuiz);

            if (response.status === 200) {
                alert('Question Added, Add more by entering following information or save quiz!')
                setNewQuestion('')
                setNewQuestionOptions({
                    optionOne: '',
                    optionTwo: '',
                    optionThree: '',
                    optionFour: ''
                })
                setCorrectAnswer('')
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    }

    return (
        <div className='create-quiz-wrapper'>
                {stepCount === 1 && (
                    <div className='section-wrapper'>
                        <div className='left'>
                            <img src={Img} alt='quizzard' />
                        </div>
                        <div className='right'>
                            <h1>Welcome to our Quiz Leaderboard!</h1>
                            <br />
                            <br />
                            {/* <Select placeholder="Select Category" options={options} onChange={handleCategoryChange} /> */}
                            <input placeholder="Please enter category" type='text' value={enteredCategory} onChange={(e) => setEnteredCategory(e.target.value)} />
                            <br />
                            <br />
                            <br />
                            <div className='button-wrapper'>
                                <button className='add-question-button' onClick={() => {
                                    if(enteredCategory) setStepCount(2)
                                }}>
                                    ADD QUESTIONS
                                </button>
                            </div>
                        </div>
                    </div>
                )}
                {stepCount === 2 && (
                    <div className='add-quiz-question-wrapper'>
                        <br />
                        <h1>Add Question</h1>
                        <br />
                        <input className='input' placeholder="Please enter question" type='text' value={newQuestion} onChange={(e) => setNewQuestion(e.target.value)} />
                        <br />
                        <br />
                        <h2>Tap on any answer to select the correct one!</h2>
                        <br />
                        <br />
                        <div className='input-option-wrapper'>
                            <input className={`input-option ${correctAnswer === newQuestionOptions.optionOne ? 'correct-answer' : ''}`} placeholder='Option One' value={newQuestionOptions.optionOne} onClick={() => setCorrectAnswer(newQuestionOptions.optionOne)} onChange={(e) => setNewQuestionOptions(pQ => ({ ...pQ, optionOne: e.target.value}))} />
                            <input className={`input-option ${correctAnswer === newQuestionOptions.optionTwo ? 'correct-answer' : ''}`} placeholder='Option Two' value={newQuestionOptions.optionTwo} onClick={() => setCorrectAnswer(newQuestionOptions.optionTwo)} onChange={(e) => setNewQuestionOptions(pQ => ({ ...pQ, optionTwo: e.target.value}))} />
                            <input className={`input-option ${correctAnswer === newQuestionOptions.optionThree ? 'correct-answer' : ''}`} placeholder='Option Three' value={newQuestionOptions.optionThree} onChange={(e) => setNewQuestionOptions(pQ => ({ ...pQ, optionThree: e.target.value}))} onClick={() => setCorrectAnswer(newQuestionOptions.optionThree)} />
                            <input className={`input-option ${correctAnswer === newQuestionOptions.optionFour ? 'correct-answer' : ''}`} placeholder='Option Four' value={newQuestionOptions.optionFour} onChange={(e) => setNewQuestionOptions(pQ => ({ ...pQ, optionFour: e.target.value}))} onClick={() => setCorrectAnswer(newQuestionOptions.optionFour)} />
                        </div>
                        <br />
                        <br />
                        <div className='button-wrapper'>
                            <button className='add-new-question-button' onClick={async () => {
                                if(!newQuestion) {
                                    alert('Please enter question!')
                                    return
                                }
                                if(!newQuestionOptions.optionOne || !newQuestionOptions.optionTwo || !newQuestionOptions.optionThree || !newQuestionOptions.optionFour) {
                                    alert('Please enter all question options!')
                                    return
                                }
                                await handleQuestionAdd()
                            }}>
                                ADD QUESTION
                            </button>
                            <button className='save-quiz-button' onClick={() => {
                                setNewQuestion('')
                                setNewQuestionOptions({
                                    optionOne: '',
                                    optionTwo: '',
                                    optionThree: '',
                                    optionFour: ''
                                })
                                setCorrectAnswer('')
                                setEnteredCategory('')
                                setStepCount(1)
                            }}>
                                GO BACK
                            </button>
                        </div>
                    </div>
                )}
        </div>
    )
}

export default CreateQuiz