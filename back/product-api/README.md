# Instruction to run

## 1. Run mysql database

To run this spring boot application you must have a mysql database.
You have to possibilities.
In all cases, using JPA enables automatic table creation. So you won't need to import an sql file.

### 1.1 Install mysql on your machine

Install mysql : https://www.mysql.com/

Create a database with the following parameters :

| Param    | Value    |
| -------- | -------- |
| DATABASE | database |
| USER     | alten    |
| PASSWORD | alten    |

### 1.2 Or use docker compose

For the purpose of this exercise I give you a `docker-compose.yml` file with all the necessary parameters
To run this file in a terminal : `docker compose up`

## 2. Run the api

After running mysql you have two possibilites to run the api :

### 2.1 With IDE

Run the main method

### 2.1 With maven

run `mvn spring-boot:run`

### 2.3 With the jar

run `java -jar product-api.jar`

# Postman test:

On the `doc/` folder you can find a json dump of postman collection.
You can import it to postman to access of all tests.

# Route implementation

All of the following routes were implemented

| Resource        | POST                  | GET                            | PATCH                                    | PUT | DELETE           |
| --------------- | --------------------- | ------------------------------ | ---------------------------------------- | --- | ---------------- |
| **/products**   | Create a new products | Retrieve all products          | X                                        | X   | X                |
| **/products/x** | X                     | Retrieve details for product x | Update details of product x if it exists | X   | Remove product x |
