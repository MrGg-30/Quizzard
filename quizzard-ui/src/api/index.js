import axios from 'axios';
import { config } from '../Constants'

const API = axios.create({
    baseURL: config.url.API_BASE_URL
})

function bearerAuth(token) {
    return `Bearer ${token}`
}

function getUserByToken(token) {
    return API.get(`/user/fetch-user`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const uploadUserProfilePic = async (username, file, token) => {
    await API.post(`/user/picture?username=${username}`, file, {
        headers: { 'Authorization': bearerAuth(token), "Content-Type": "multipart/form-data" }
    })
} 

function createUser(keycloak) {
    const userObject = {
        username: keycloak.tokenParsed.preferred_username,
        email: keycloak.tokenParsed.email,
        name: keycloak.tokenParsed.given_name,
        lastName: keycloak.tokenParsed.family_name
    };

    return API.post(`/user/create`, userObject, {
        headers: { 'Authorization': bearerAuth(keycloak.token) }
    })
}

const getUsername = async (token) => {
    return API.get(`/user/username`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const getUserProfileByUsername = async (token, username) => {
    return API.get(`/user/profile/${username}`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const getUserPictureByUsername = async (token, username) => {
    return API.get(`/user/picture/${username}`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const fetchUserWithoutParams = async (token) => {
    return API.get("/user/fetch-user", {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const fetchUserEmailWithoutParams = async (token) => {
    return API.get("/user/email", {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const fetchQuizCategoriesWithoutParams = async (token) => {
    return API.get("/quiz/categories", {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const getQuizQuestionsByCategory = async (token, category) => {
    return API.get(`/quiz/questions/${category}`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const getSingleCategory = async (token, category) => {
    return API.get(`/quiz/categories/${category}`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const getPendingRequestsByUsername = async (token, username) => {
    return API.get(`/pendingRequests/${username}`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const getUserbySearchPrefix = async (token, username) => {
    return API.get(`/user/${username}`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}
const sendFriendRequest = async (token, data) => {
    return API.post(`/friends/request`, data, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}
const updateFriendRequest = async (token, data) => {
    return API.post(`/friends/response`, data, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const getLeaderBoardByCategory = async (token, category) => {
    return API.get(`/leaderboard/rating/${category}`, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const createQuiz = async (token, newQuiz) => {
    return API.post(`/quiz`, newQuiz, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

const submitQuiz = async (token, results) => {
    return API.post(`/quiz/result`, results, {
        headers: { 'Authorization': bearerAuth(token) }
    })
}

export const Api = {
    getUserByToken,
    createUser,
    uploadUserProfilePic,
    getUsername,
    getUserProfileByUsername,
    getUserPictureByUsername,
    fetchUserWithoutParams,
    fetchUserEmailWithoutParams,
    fetchQuizCategoriesWithoutParams,
    getQuizQuestionsByCategory,
    getSingleCategory,
    getPendingRequestsByUsername,
    getLeaderBoardByCategory,
    createQuiz,
    submitQuiz,
    getUserbySearchPrefix,
    sendFriendRequest,
    updateFriendRequest
}

