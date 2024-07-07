Spring Boot CRUD application with RESTful APIs that uses PostgreSQL and Liquibase for database migration

# ENVIRONMENT
OS: Ubuntu 22.04.4 LTS\
Java: 17.0.11\
Maven: 3.8.5\
PostgreSQL: 16.3

# SETUP

### Database
If running PostgreSQL locally create user and database
```shell
sudo -u postgres psql
CREATE USER product_owner WITH PASSWORD 'product_owner';
CREATE DATABASE product_db OWNER product_owner ENCODING UTF8 LOCALE 'sl_SI.UTF-8' TEMPLATE template0;
```
Alternatively, run postgres in a docker container
```shell
docker run --name pg1 -p 5432:5432 -e POSTGRES_USER=product_owner -e POSTGRES_PASSWORD=product_owner -e POSTGRES_DB=product_db -d postgres:16.3
```
Required table is created and dummy data inserted using liquibase when application runs.

# RUN
Run the application in any of the following ways
```shell
./mvnw clean spring-boot:run
# or
mvn clean spring-boot:run
# or build and run with java
mvn clean package
java -jar target/product-backend-0.0.1-SNAPSHOT.jar
```

# TEST
Test REST API with curl commands

```shell
# list all products
curl -v localhost:8080/products

# get one product
curl -v localhost:8080/products/Shirt

# get one product that does not exist - returns error message
curl -v localhost:8080/products/Banana

# create new product. throws error if trying to create a product with existing name 
curl -X POST localhost:8080/products -H 'Content-type:application/json' -d '{"name": "Kiwi", "description": "Kiwi product description", "price": "12.58"}'

# update existing product. throws error if trying to update product that does not exist
curl -X PUT localhost:8080/products -H 'Content-type:application/json' -d '{"name": "Toothbrush", "description": "Updated toothbrush description", "price": "77.25"}'

# delete existing product
curl -X DELETE localhost:8080/products/Toothbrush
```
