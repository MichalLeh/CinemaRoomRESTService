# Cinema Room REST Service

Simple Spring Boot REST service implementation of [Hyperskill's](https://hyperskill.org/projects/189) project that handles HTTP requests in controllers, creates services and responds with JSON objects.

REST service can show the available seats, purchase and refund tickets, and display the statistics of the small movie theater. 

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
      {
         "row":1,
         "column":3
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

**Example 3:** a `POST /purchase` *request, the ticket is already booked*

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

**Example 4:** a `POST /purchase` *request, a wrong row number*

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
**Example 5:** a `POST /return` *with the correct token*

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
**Example 6:** a `POST /return` *with an expired token*

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

**Example 7:** a `POST /stats` *request with no or wrong parameter*

*Response body::*

```
{
    "error": "The password is wrong!"
}
```
**Example 8:** a `POST /stats` *request with the correct password*

*Response body:*
```
{
    "current_income": 0,
    "number_of_available_seats": 81,
    "number_of_purchased_tickets": 0
}
```
