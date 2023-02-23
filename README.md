# Cinema Room REST Service

Simple Spring Boot REST service implementation of [Hyperskill's](https://hyperskill.org/projects/189) project that handles HTTP requests in controllers, creates services and responds with JSON objects.

REST service can show the available seats, purchase and refund tickets, and display the statistics of the small movie theater. 

## Usage

1. Clone the repository
    ```shell
    git clone https://github.com/MichalLeh/CinemaRoomRESTService.git
    ```

2. Build and run the project
    ```shell
    cd CinemaRoomRESTService
    ./gradlew build
    ./gradlew bootRun
    ```

The endpoints can be accessed using a browser or a tool that allows you to send HTTP requests
like [Postman](https://www.getpostman.com/).

### Processes

- [Seats](#seats)
- [Purchase a seat](#purchase-a-ticket)
- [Return a ticket](#return-a-ticket)
- [Stats](#stats)

## API Endpoints

| Endpoint                  | No password | Password |
|---------------------------|-------------|----------|
| GET  /seats               | +           | -        |
| POST /purchase            | +           | -        |
| POST /return              | +           | -        |
| POST /stats               | -           | +        |

_'+' means there is no need for password to access that endpoint. '-' means you need a password key with a "super_secret" value as parameter to access that endpoint._

### Examples

#### Seats

`GET /seats` *request*

*Response body:*

```
{
   "total_rows":9,
   "total_columns":9,
   "available_seats":[
      {
         "row":1,
         "column":1
      },
      {
         "row":1,
         "column":2
      },
      
      ........

      {
         "row":9,
         "column":8
      },
      {
         "row":9,
         "column":9
      }
   ]
}
```
#### Purchase a ticket

`POST /purchase` *correct request*

*Request body:*

```
{
    "row": 2,
    "column": 3
}
```
*Response body:*
```
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556",
    "ticket": {
        "row": 2,
        "column": 3,
        "price": 10
    }
}
```
`POST /purchase` *request, the ticket is already booked*

*Request body:*

```
{
    "row": 2,
    "column": 3
}
```
*Response body:*
```
{
    "error": "The ticket has been already purchased!"
}
```
`POST /purchase` *request, a wrong row number*

*Request body:*

```
{
    "row": 11,
    "column": 2
}
```
*Response body:*
```
{
    "error": "The number of a row or a column is out of bounds!"
}
```
#### Return a ticket

`POST /return` *with the correct token*

*Request body:*

```
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
}
```
*Response body:*
```
{
    "returned_ticket": {
        "row": 2,
        "column": 3,
        "price": 10
    }
}
```
`POST /return` *with an expired token*

*Request body:*

```
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
}
```
*Response body:*
```
{
    "error": "Wrong token!"
}
```
#### Stats

`POST /stats` *request with no or wrong parameter*

*Response body::*

```
{
    "error": "The password is wrong!"
}
```
`POST /stats` *request with the correct password*

*Response body:*
```
{
    "current_income": 0,
    "number_of_available_seats": 81,
    "number_of_purchased_tickets": 0
}
```
## Stack

- Java 11.0.11
- Gradle 7.3.3
- Spring Boot 2.7.2
