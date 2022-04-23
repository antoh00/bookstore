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
Let's first include invalid email, then we will get following result. As you can see message body is ```email not valid```
<br>We registered as publisher, and from image we can see that it will return message ```confirmation link has been sent to your email address```
<br> Above of this message there is ```confirmUrl```. This is just for testing purpose. For production deploy we will remove it for security.
We added ```GMAIL``` for sending emails for users and publishers. But when we deploy it to server, Google block my email sender service. So if you want to check this project, clone it to your pc and run it from localhost, in that case everything should be OK.

![image](https://user-images.githubusercontent.com/56788221/164908922-fd29b07f-a155-42ed-896a-0f062e3da1cf.png)

If we again type previously registered email then get a message: ```email already taken```

![image](https://user-images.githubusercontent.com/56788221/164909142-9638f9a8-209d-4cfb-85ad-a2bc098ab025.png)


Now, if we enter incorrect email format we wil get a message: ```email not valid```

![image](https://user-images.githubusercontent.com/56788221/164909054-06cb32e7-f91e-4a22-bab0-f9b3b2ab374d.png)

### 2. POST ```api/user/register``` registration for Users

#### All images depicted above is also the same for this endpoint. But there is one difference. From first endpoint only ```publishers``` can register, but from the second endpoint only ```users``` can register.

### 3. GET ```api/register/confirm``` confirmation of registered email

From very first image you can see ```confirmUrl``` key on json response. So, now let's type its value on Postman and see the message: ```Your email has been confirmed!```

![image](https://user-images.githubusercontent.com/56788221/164909457-02a69a5d-0aae-46f9-8bb8-623af5a26ff0.png)

If we again click the same url, then we will get following message: ```Your registration token has been expired```.
Remember that we set expiration date as ```15 minutes```. If you try to access the link for example 16 minutes after sending it, then you will get this message again!

![image](https://user-images.githubusercontent.com/56788221/164909518-b740b114-080c-4787-9a9f-3202e02f198c.png)

### 4. POST ```api/login``` after successfully registration and confirmation, using this endpoint you can login

After successfull confirmation of your account, we can post ```username``` - (email) and ```password``` - password to login our application.

![image](https://user-images.githubusercontent.com/56788221/164909901-4058afdd-62d0-466f-8bba-cbae79b624bf.png)

But if we type either username or password incorrect it will show us message ```bad credentials```

### 5. GET ```api/profile``` if Publisher or User successfully logged-in, then redirected to this endpoint

If user or publisher login successfully, then ```/api/login``` will be redirected to ```/api/profile``` endpoint

### 6. GET ```api/book/all``` both of users and publishers can see all books
### 7. GET ```api/book/all?pageNo=0&pageSize=2``` Pagination is applied
### 8. POST ```api/book/add``` add new book
### 9. GET ```api/book/{bookId}``` both of Publisher and User can see book by id
### 10. PUT ```api/book/update/{bookId}``` Publisher can update his own books
### 11. DELETE ```api/book/delete/{bookId}``` Publisher can delete his own books


