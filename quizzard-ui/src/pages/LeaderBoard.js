import { useState, useEffect } from 'react';
import Select from 'react-select'

import { Api } from '../api'
import Img from '../assets/images/search-quizzard-right.png'
import '../scss/leaderboard.scss'

const CategorySelection = ({ categories, handleCategoryChange }) => {
    const options = categories?.map(c => ({ value: c, label: c }))

    return (
        <div className='leaderboard-category-selection'>
            <div className='section-wrapper'>
                <div className='left'>
                    <h1>Welcome to our Quiz Leaderboard!</h1>
                    <br />
                    <p>
                        To view the leaderboard for your desired quiz
                        <br />
                        category, please enter the quiz category name in
                        <br />
                        the space provided below.
                    </p>
                    <br />
                    <br />
                    <Select placeholder="Category" options={options} onChange={handleCategoryChange} />
                </div>
                <div className='right'>
                    <img src={Img} alt='quizzard' />
                </div>
            </div>
        </div>
    )
}

function LeaderBoard({ keycloak }) {
    const [showCategorySelectionScreen, setShowCategorySelectionScreen] = useState(true);
    const [categories, setCategories] = useState([])
    const [selectedCategory, setSelectedCategory] = useState('')
    const [leaderboardDetails, setLeaderboardDetails] = useState(undefined)

    const hiderShowCategoryScreen = () => setShowCategorySelectionScreen(false)

    const getAndSetCategories = async () => {
        try {
            const response = await Api.fetchQuizCategoriesWithoutParams(keycloak.token);

            if (response.status === 200) {
                setCategories(response.data);
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    }

    const getAndSetLeaderboard = async (cat) => {
        try {
            const response = await Api.getLeaderBoardByCategory(keycloak.token, cat);

            if (response.status === 200) {
                setLeaderboardDetails(response.data);
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    }

    const handleCategoryChange = (cat) => {
        getAndSetLeaderboard(cat?.value)
        setSelectedCategory(cat?.value);
        hiderShowCategoryScreen();
    }

    useEffect(() => {
        if (keycloak?.token) {
            getAndSetCategories()
        }
    }, [])

    console.log({ leaderboardDetails })

    return (
        <div className="leader-board">
            {showCategorySelectionScreen ? (
                <CategorySelection categories={categories} handleCategoryChange={handleCategoryChange} />
            ) : (
                <>
                    <p className="title">
                        Top Performers in <b>{selectedCategory}</b>
                    </p>
                    <div className="left">

                        <div className="position-bars">
                            <div className="p2">2
                                <div className="badge">
                                    <img className="dp" src="/media/default-dp.png" alt="dp" />
                                    <img className="m" src="/media/p2.png" alt="img" />
                                </div>
                            </div>
                            <div className="p1">1
                                <div className="badge">
                                    <img className="dp" src="/media/default-dp.png" alt="dp" />
                                    <img className="m" src="/media/p1.png" alt="img" />
                                </div>
                            </div>
                            <div className="p3">3
                                <div className="badge">
                                    <img className="dp" src="/media/default-dp.png" alt="dp" />
                                    <img className="m" src="/media/p3.png" alt="img" />
                                </div>
                            </div>
                        </div>


                    </div>
                    <div className="right">
                        <div className="point-list">
                            {leaderboardDetails?.length > 0 ? leaderboardDetails?.map((item, index) => {
                                return (
                                    <div className="point" key={index}>
                                        <div className="medal">
                                            <img src="/media/medal.png" alt="" />
                                            <span> {3 + index + 1}</span>
                                        </div>
                                        <p className="name">{item?.username}</p>
                                        <span className="scores">{item?.score} pt</span>
                                    </div>
                                )
                            })
                                :
                                <div style={{ textAlign: "center", marginTop: 50 }}>
                                    <h1>Data Not Found</h1>
                                </div>
                            }
                        </div>
                    </div>
                </>
            )}
        </div>
    );
}

export default LeaderBoard;