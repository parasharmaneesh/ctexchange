
# Getting Started

Rest-API for the BTC-USD exchange
Assumptions and Instructions to build and run the project




## Assumptions

- DB auto ddl is set to update to create tables on app startup
- Considering the time constraint app has scope of improvement like more test cases, Netty, configurable configuration etc.  

## Prerequisites

To run this project, you will need to setup the following

- JDK 11 or newer
- Maven
- Postgres


## DB setup (required if running without docker)

- Create a db with name "ctexchange"
- update username and password in application.yml 

## Build & Run 

Build project

```bash
  mvn clean package
```

Run test

```bash
  mvn test
```

## Start the application without docker

```bash
  mvn spring-boot:run
```

## Start application using docker

Setup docker and use docker compose to up containers 

```bash
  docker-compose up -d
```

## Demo

Application will start on port 8080 and api swagger documentation can be accessed using following URL


```bash
  http://localhost:8080/swagger-ui/index.html
```
