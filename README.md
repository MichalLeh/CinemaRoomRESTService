# Cinema Room REST Service

Simple Spring Boot REST service implementation of [Hyperskill's](https://hyperskill.org/projects/189) project that handles HTTP requests in controllers, creates services and responds with JSON objects.

REST service can show the available seats, purchase and refund tickets, and display the statistics of the small movie theater. 

## Usage

1. Clone the repository
    ```shell
    git clone https://github.com/dan-koller/Spring-Recipes
    ```

2. Setup the environment
    ```shell
    cd Spring-Web-Quiz-Engine
    cp .env.example .env
    ```
   Edit the `.env` file to your needs. Since the project uses H2 as a database, you just need to
   the `H2_DATABASE_URL`, `H2_DATABASE_USERNAME` and `H2_DATABASE_PASSWORD` variables. The default values are:
    ```shell
    H2_DATABASE_URL=h2:file:../quizdb
    H2_DATABASE_USERNAME=sa
    H2_DATABASE_PASSWORD=
    ```

3. Build and run the project
    ```shell
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

**Example 1:** a `GET /seats` *request*

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

**Example 2:** a `POST /purchase` *correct request*

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
**Example 2 a:** a `POST /purchase` *request, the ticket is already booked*

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
**Example 2 b:** a `POST /purchase` *request, a wrong row number*

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

**Example 3:** a `POST /return` *with the correct token*

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
**Example 3 a:** a `POST /return` *with an expired token*

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

**Example 4:** a `POST /stats` *request with no or wrong parameter*

*Response body::*

```
{
    "error": "The password is wrong!"
}
```
**Example 4 a:** a `POST /stats` *request with the correct password*

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
- Gradle 7.6
- Spring Boot 2.7.2
