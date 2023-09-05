# Quizzard application

Welcome to **Quizzard**, your ultimate destination for a fun and engaging quiz experience! Quizzard is a feature-packed quiz application that allows you to challenge your knowledge, compete with friends, and even create your own quizzes.

## Features
- **Single Player Mode**: Test your knowledge and beat your own high scores by playing quizzes in single-player mode.

- **Multiplayer Mode**: Challenge your friends in real-time multiplayer quizzes. Prove that you are the ultimate quiz champion!

- **Add Friends**: Connect with friends within the app and compete against each other. Stay updated with their quiz scores and achievements.

- **Create Quizzes**: Unleash your creativity by creating your own quizzes. Share them with your friends and the quizzard community.

- **Leaderboard**: Track your progress and see how you stack up against other players on the global leaderboard. Compete for the top spot and earn bragging rights!


## Applications

- ### quizzard-api

  `Spring Boot` Web Java backend application that exposes a REST API to manage **quizzes**. Its secured endpoints can just be accessed if an access token (JWT) issued by `Keycloak` is provided.

  `quizzard-api` stores its data in a [`Mongo`](https://www.mongodb.com/) database.


- ### quizzard-ui

    `React` frontend application where `users` can create and play quizzes. In order to access the application, `user` must login using his/her username and password.
Those credentials are handled by `Keycloak`. All the requests coming from `quizzard-ui` to secured endpoints in `quizzard-api` have a access token (JWT) that is generated when `user` logs in.

## Prerequisites

- [`Java 17+`](https://www.oracle.com/java/technologies/downloads/#java17)
- [`npm`](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
- [`Docker`](https://www.docker.com/)

## Start Environment

- In a terminal and inside `docker` root folder run
  ```
  docker compose up -d
  ```

- Wait for all Docker containers to be up and running. To check it, run
  ```
  docker compose ps
  ```
## Applications URLs

| Application  | URL                                   | 
|--------------|---------------------------------------|
| quizzard-api | http://localhost:8081/swagger-ui/index.html  |  
| quizzard-ui  | http://localhost:3000                 |        
| Keycloak     | http://localhost:8080/admin           |                        

## Shutdown

- To stop `quizzard-api` and `quizzard-ui`, go to the terminals where they are running and press `Ctrl+C`

- To stop and remove docker compose containers, network and volumes, go to a terminal and, inside `docker` root folder, run the command below
  ```
  docker compose down -v
  ```

## How to upgrade quizzard-ui dependencies to latest version

- In a terminal, make sure you are in `/quizzard-ui` folder

- Run the following commands
  ```
  npm upgrade
  npm i -g npm-check-updates
  ncu -u
  npm install
  ```