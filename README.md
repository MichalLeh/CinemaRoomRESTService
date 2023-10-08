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

- The endpoint must respond with the `HTTP OK` status `200` and return information about the rows, columns and available seats in a JSON format.

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
- Endpoint that marks a booked ticket as purchased and should contain the following data:
    - `row` the row number;
    - `column` the column number.

*Request body:*

```
{
    "row": 2,
    "column": 3
}
```
- If the purchase is successful, the response body return row, column, price and token;
- The ticket price is determined by a row number. If the row number is less or equal to 4, set the price at 10. All other rows cost 8 per seat.
  
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
- If the seat is taken, respond with `Bad Request` status `400` code and following response body:

*Response body:*
```
{
    "error": "The ticket has been already purchased!"
}
```
`POST /purchase` *request, a wrong row number*
- If users pass a wrong row/column number, respond with `Bad Request` status `400` code and following line:

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
- Endpoint will allow customers to refund their tickets;
- The request should have the `token` feature that identifies the ticket in the request body. Once you have the token, you need to identify the ticket it relates to and mark it as available.
  
*Request body:*

```
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
}
```
- If the return is successful, respond with the `HTTP OK` status `200` and the following response body:
 
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
- If you cannot identify the ticket by the token, respond with `Bad Request` status `400` code and the following response body:

*Response body:*
```
{
    "error": "Wrong token!"
}
```
#### Stats

`POST /stats` *request with the correct password*
- If the URL parameters contain a password key with a "super_secret" value, respond with the `HTTP OK` status `200` and return the movie theatre statistics in following format:
    - `income` shows the total income of sold tickets.
    - `available` shows how many seats are available.
    - `purchased` shows how many tickets were purchased.
      
*Response body:*
```
{
    "current_income": 0,
    "number_of_available_seats": 81,
    "number_of_purchased_tickets": 0
}
```
`POST /stats` *request with no or wrong parameter*
- If the parameters don't contain a password key or a wrong value has been passed, respond with `UNAUTHORIZED` status `401`.
  
*Response body::*

```
{
    "error": "The password is wrong!"
}
```

## Stack

- Java 11.0.11
- Gradle 7.3.3
- Spring Boot 2.7.2
