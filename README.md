# Update 25.04.2022
I just received an email from heroku. There was mentioned that the database url shouldn't be 
public that's why they changed it. If APİ link should not work for you then clone it to your PC and rename database url, username and password in ```application.properties``` file!

# Complete Book Store Rest API

This project was given for selecting students for the first stage of Ingress Microservices BootCamp. Each endpoint from task is covered in this documentation. 

# Attention

Remember that, I used free Heroku server, so included api may be responded slowly. Please consider it while testing API

# Feedback
If you have any question or feedback about api kindly contact to me from LinkedIn: [linkedin](https://www.linkedin.com/in/eltac-shikhsaidov/)

# Usage

I used GMAIL as email service for this application. Whenever I deployed it to heroku, Google blocks my email service. So, if you want to test my API, then you can run it locally and test each endpoint. I did not included every step below. Because if I included each of them there would ton of images and you will be bored to read my explanation. Just run it locally for checking the parts which you interested in.

# Technologies used for this project
- ```Spring Data JPA```
- ```Spring Security```
- ```Spring Web```
- ```Lombok```
- ```PostgreSQL```
- ```Heroku```
- ```Slf4J```

# Requirement for running locally

- ```Postman``` - for testing API
- ```IntelliJ IDEA (or VS code)``` for opening project, for checking code
- ```Java``` configure java on your pc

# How to run locally

- step 1. ```make sure that, you have installed java to your pc and added it to system path```
- step 2. ```Make sure that, you have Visual Studio Code or IntelliJ IDEA (preffered)```
- step 3. ```Clone project to your pc```
- step 4. ```Open it with your favorite editor```
- step 5. ```Do not change anything```
- step 6. ```Run it```
- step 7. ```Test endpoints you are interested in```

# If you don't want to run locally

I deployed API to heroku, but removed email sending option from it

[api link](https://taskingress.herokuapp.com/)

or

https://taskingress.herokuapp.com/ if you just open this link it will not work, try endpoints given below!

Just test this api on Postman.


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

![image](https://user-images.githubusercontent.com/56788221/164909054-06cb32e7-f91e-4a22-bab0-f9b3b2ab374d.png)

<br>We registered as publisher, and from image we can see that it will return message ```confirmation link has been sent to your email address```

![image](https://user-images.githubusercontent.com/56788221/164908922-fd29b07f-a155-42ed-896a-0f062e3da1cf.png)

<br> Above of this message there is ```confirmUrl```. This is just for testing purpose. For production deploy we will remove it for security.
We added ```GMAIL``` for sending emails for users and publishers. But when we deploy it to server, Google block my email sender service. So if you want to check this project, clone it to your pc and run it from localhost, in that case everything should be OK.

If we again type previously registered email then get a message: ```email already taken```

![image](https://user-images.githubusercontent.com/56788221/164909142-9638f9a8-209d-4cfb-85ad-a2bc098ab025.png)

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

![image](https://user-images.githubusercontent.com/56788221/164910091-f66140e3-389a-4109-8ddc-806fd6ef3e8b.png)

In case user logins, then instead of ```"role": "PUBLISHER"```, there would be ```"role": "USER"```, ```totalBooksYouPublished``` and ```books``` also would not added for ```user```

### 6. POST ```api/book/add``` add new book

So, now let's add some books as ```PUBLISHER```. Because we logged in as ```PUBLISHER```, we can add books. If we logged in as ```USER```, then we can not add books

![image](https://user-images.githubusercontent.com/56788221/164910394-99d7b796-23ae-4bfb-a666-81915c445959.png)

Let's add 10-15 books and go to ```/api/profile```. Then we will get:
We added 9 books by same publisher. ```books``` is also return a json array. I minimized it in order to see the json file clearly

![image](https://user-images.githubusercontent.com/56788221/164910502-ef02f32f-adcc-4a45-a05c-da9b3d990203.png)



### 7. GET ```api/book/all``` both of users and publishers can see all books

By default, if we call this endpoint, then we will get only ```3``` element from ```books``` json array. Because, in the backend by default we specified 3 books per page

![image](https://user-images.githubusercontent.com/56788221/164910688-e49745cb-1d7d-40bb-879b-a971ae660471.png)

### 8. GET ```api/book/all?pageNo=0&pageSize=2``` Pagination is applied
If we specify ```pageNo``` and ```pageSize``` parameters and set them values then, then we will get list of books per page as we specified them.
For example ```pageNo=0``` and ```pageSize=2``` will display first two books for the first page:

So we can say that ```Pagination``` is working correctly

![image](https://user-images.githubusercontent.com/56788221/164910814-40c40dd5-6440-44bf-b6e9-b6e9bdf97afa.png)

### 9. GET ```api/book/{bookId}``` both of Publisher and User can see book by id

Get book by id

![image](https://user-images.githubusercontent.com/56788221/164910871-d3029e4a-3eac-4112-a07e-fa71598f9362.png)

If id is not in the books list then:

![image](https://user-images.githubusercontent.com/56788221/164910898-275e06dc-abdf-46bf-ac8b-b78cdaadd711.png)

### 10. PUT ```api/book/update/{bookId}``` Publisher can update his own books

If book published by logged in publisher, then he can update and delete the book. But he can not update and delete other publishers books!

![image](https://user-images.githubusercontent.com/56788221/164910996-8abba6f1-7256-4387-a50c-a27f377d1032.png)

### 11. DELETE ```api/book/delete/{bookId}``` Publisher can delete his own books

Publisher can delete book, if and only if he published this book. He can not delete other publishers books

![image](https://user-images.githubusercontent.com/56788221/164911024-f4d09346-11a3-408b-a840-d9d4a9e79f15.png)



