# Application Overview, Cloud Services, and Connections

## Overview
This project bla

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
- **API Gateway**:
- **Cognito**:
  - Cognito is used along with Cognito Pools in order to help authenticate and authorize the users of the application
  - The keys recieved from this service help make API calls and validate the user within the application
- **IAM**:
  - A dedicated IAM user is used primarily to use the cloud services with its roles and permissions
