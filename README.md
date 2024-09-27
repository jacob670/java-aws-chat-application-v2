# Application Overview, Cloud Services, and Connections

got to fix the movie search id. if you spell it wrong program just breaks. needs to return bad response bla bla
got to add token experitation and better login experience.

## Overview
The application is built using the Spring Boot Framework for the server side. The client side is built using the React.js framework. Additionally, AWS cloud services are utilized in order to have a scalable and efficient app. 
- **[Client Side in React.js]** (https://github.com/jacob670/react-aws-chat-application-v2-frontend)

## Key Features
- **Serverless REST API**:
- **Authorization and Authentication**:
- **Serverless CRUD API and Database**:
- **Scalability**:

## Cloud Services
- **Lambdas**:
  - 2 different types of lambdas functions
    - CRUD API Actions
      - Create, Read, Update, Delete from a serverless database is used due to it being a primary feature in the application
    - External API Requests
      - These requests are made from The Movie Database(TMDB) API and are incorporated in the application for information
- **DynamoDB**:
  - A NoSQL database stores a variety of features related to the application
  - The primary key is the idToken, which will map and sort the users data in the table
- **API Gateway**:
  - 2 separate APIs are built and used
    - Serverless CRUD API
      - This API is used for CRUD actions for a specific feature of the app
    - Movies REST API
      - This API uses lambdas that are written along with external APIs, which are then called in the client-side
- **Cognito**:
  - Cognito is used along with Cognito Pools in order to help authenticate and authorize the users of the application
  - The keys recieved from this service help make API calls and validate the user within the application
- **IAM**:
  - A dedicated IAM user is used primarily to use the cloud services with its roles and permissions
