# TestClevertecTask

Spring boot REST application is using for:

1) Creating the cash receipt by params appended to the url, returning it as JSON data and writing it into the .pdf file;
2) Creating the cash receipt by incoming data from the .txt file, returning it as JSON data and writing it into the .pdf
   file;
3) Also, application has possibility to work with DiscountCard and Product entities.
   The application has two profiles 'Prod' and 'Test'. 'Prod' uses PostgresSQL database, 'Test' uses H2 embedded
   database.

## How to use

Docker-compose.yml file contains all the needed for running this application using Docker.
First, fill the database using data.sql file. After you can use application by its entry points.

## Application entry points

### Get cash receipt by transferred 'GET' request parameters

GET http://localhost:8088/clevertecTestTask/v1/cashReceipt?productId=1&productId=2&productId=1&card=1

Result:

1) Created file "cashReceipt.pdf" in the project folder
2) Content-Type: application/json
3) RESPONSE: HTTP 201 CREATED

```json
{
  "cashierDto": {
    "cashierId": 1
  },
  "shopDto": {
    "shopName": "SUPERMARKET 123",
    "shopAddress": "12, MILKY WAY Galaxy/ Earth",
    "shopPhone": "+375(17) 362-17-60"
  },
  "cashReceiptItemList": [
    {
      "productName": "Blueberry Jam",
      "productQuantity": 1,
      "productPrice": 25.12,
      "productTotalPrice": 22.61
    },
    {
      "productName": "Strawberry Jam",
      "productQuantity": 2,
      "productPrice": 14,
      "productTotalPrice": 26.6
    }
  ],
  "discountCardDto": {
    "id": 1,
    "cardDiscountPercent": 5
  },
  "cashReceiptDate": "2022/12/20",
  "cashReceiptTime": "22:16:51",
  "totalDiscount": 3.91,
  "totalPrice": 49.21
}
```

### Get cash receipt by incoming data from the .txt file passing path to the file using 'POST' request parameters

POST http://localhost:8088/clevertecTestTask/v1/cashReceipt

Body:

```json
{
  "path": "D:/clevertec.txt"
}
```

Example of the .txt file content:

```text
2=1 1=2 card=1
```

Result:

1) Created file "cashReceipt.pdf" in the project folder
2) Content-Type: application/json
3) RESPONSE: HTTP 201 CREATED

```json
{
  "cashierDto": {
    "cashierId": 1
  },
  "shopDto": {
    "shopName": "SUPERMARKET 123",
    "shopAddress": "12, MILKY WAY Galaxy/ Earth",
    "shopPhone": "+375(17) 362-17-60"
  },
  "cashReceiptItemList": [
    {
      "productName": "Blueberry Jam",
      "productQuantity": 1,
      "productPrice": 25.12,
      "productTotalPrice": 22.61
    },
    {
      "productName": "Strawberry Jam",
      "productQuantity": 2,
      "productPrice": 14.0,
      "productTotalPrice": 26.6
    }
  ],
  "discountCardDto": {
    "id": 1,
    "cardDiscountPercent": 5
  },
  "cashReceiptDate": "2022/12/20",
  "cashReceiptTime": "22:36:23",
  "totalDiscount": 3.91,
  "totalPrice": 49.21
}
```

### Get product by id(example id = 1)

GET http://localhost:8088/clevertecTestTask/v1/product/1

Result: 

1) Content-Type: application/json
2) RESPONSE:  HTTP 200 OK
```json
{
  "productName": "Strawberry Jam",
  "price": 14.0,
  "productDiscountPercent": 0
}
```

### Get all products with pagination

Result:

1) Content-Type: application/json
2) RESPONSE:  HTTP 200 OK

```json
{
  "content": [
    {
      "productName": "Strawberry Jam",
      "price": 14.0,
      "productDiscountPercent": 0
    },
    {
      "productName": "Blueberry Jam",
      "price": 25.12,
      "productDiscountPercent": 10
    },
    {
      "productName": "Marshmello",
      "price": 5.6,
      "productDiscountPercent": 15
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "unpaged": false,
    "paged": true
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 3,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 3,
  "empty": false
}
```
### Save product

POST http://localhost:8088/clevertecTestTask/v1/product

Body:

```json
{
  "productName": "Chocolate",
  "price": 15.10,
  "productDiscountPercent": 10
}
```
Result:

1) RESPONSE:  HTTP 201 CREATED

### Update product

PUT http://localhost:8088/clevertecTestTask/v1/product/1

Body:
```json
{
    "productName": "Raspberry Jam",
    "price": 18.20,
    "productDiscountPercent": 15
}
```
Result:

1) RESPONSE:  HTTP 200 OK

### Delete product

DELETE http://localhost:8088/clevertecTestTask/v1/product/1

Result:

1) RESPONSE:  HTTP 200 OK


### Get discount card by id(example id = 1)

GET http://localhost:8088/clevertecTestTask/v1/discountCard/1

Result:

1) Content-Type: application/json
2) RESPONSE:  HTTP 200 OK
```json
{
  "id": 1,
  "cardDiscountPercent": 5
}
```

### Get all discount card with pagination

Result:

1) Content-Type: application/json
2) RESPONSE:  HTTP 200 OK

```json
{
  "content": [
    {
      "id": 1,
      "cardDiscountPercent": 5
    },
    {
      "id": 2,
      "cardDiscountPercent": 10
    },
    {
      "id": 3,
      "cardDiscountPercent": 20
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 3,
  "totalPages": 1,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 3,
  "empty": false
}
```
### Save discount card 

POST http://localhost:8088/clevertecTestTask/v1/discountCard

Body:

```json
{
  "cardDiscountPercent": 6
}
```
Result:

1) RESPONSE:  HTTP 201 CREATED

### Update discount card

PUT http://localhost:8088/clevertecTestTask/v1/discountCard/1

Body:
```json
{
  "cardDiscountPercent": 3
}
```
Result:

1) RESPONSE:  HTTP 200 OK

### Delete discount card

DELETE http://localhost:8088/clevertecTestTask/v1/discountCard/1

Result:

1) RESPONSE:  HTTP 200 OK







