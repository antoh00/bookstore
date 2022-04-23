# Complete Book Store Rest API

This project was given for selecting students for the first stage of Ingress Microservices BootCamp

# Endpoint (detailed explanation of endpoints given below)

- POST ```api/publisher/register``` registration for Publishers
- POST ```api/user/register``` registration for Users
- GET ```api/register/confirm``` confirmation of registered email
- POST ```api/login``` after successfully registration and confirmation, using this endpoint you can login
- GET ```api/profile``` if Publisher or User successfully logged-in, then redirected to this endpoint
- GET ```api/book/all``` both of users and publishers can see all books
- GET ```api/book/all?pageNo=0&pageSize=2``` Pagination is applied
- POST ```api/book/add``` add new book, (only if logged in user role is ```PUBLISHER```, then he/she can add new book)
- GET ```api/book/{bookId}``` both of Publisher and User can see book by id
- PUT ```api/book/update/{bookId}``` Publisher can update his own books
- DELETE ```api/book/delete/{bookId}``` Publisher can delete his own books

# Detailed explanation of each endpoint
Now let's get detailed explanation of each endpoint

### 1. POST ```api/publisher/register``` registration for Publishers
### 2. POST ```api/user/register``` registration for Users
### 3. GET ```api/register/confirm``` confirmation of registered email
### 4. POST ```api/login``` after successfully registration and confirmation, using this endpoint you can login
### 5. GET ```api/profile``` if Publisher or User successfully logged-in, then redirected to this endpoint
### 6. GET ```api/book/all``` both of users and publishers can see all books
### 7. GET ```api/book/all?pageNo=0&pageSize=2``` Pagination is applied
### 8. POST ```api/book/add``` add new book
### 9. GET ```api/book/{bookId}``` both of Publisher and User can see book by id
### 10. PUT ```api/book/update/{bookId}``` Publisher can update his own books
### 11. DELETE ```api/book/delete/{bookId}``` Publisher can delete his own books


