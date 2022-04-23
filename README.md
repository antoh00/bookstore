# Complete Book Store Rest API

This project was given for selecting students for the first stage of Ingress Microservices BootCamp

# Endpoint (detailed explanation of endpoints given below)

- POST ```api/publisher/register``` registration for Publishers
- POST ```api/user/register``` registration for Users
- GET ```api/register/confirm``` confirmation of registered email
- POST ```api/login``` after successfully registration and confirmation, using this endpoint you can login
- GET ```api/profile``` if Publisher or User successfully logged-in, then redirected to this endpoint, otherwise it will return an error
- GET ```api/book/all``` both of users and publishers can see all books
- GET ```api/book/all?pageNo=0&pageSize=2``` Pagination is applied.
- POST ```api/book/add``` add new book, (only if logged in user role is ```PUBLISHER```, then he/she can add new book)
- GET ```api/book/{bookId}``` both of Publisher and User can see book by id
- PUT ```api/book/update/{bookId}``` Publisher can update his own books
- DELETE ```api/book/delete/{bookId}``` Publisher can delete his own books

# Detailed explanation of each endpoint

