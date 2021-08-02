# Player Type Validator
## Prerequisites
- Apache kafka 2.7.0+ (including zookeper)
- Jdk 11

## Stack
- Java 11
- Spring boot
- H2 db
- Kafka

## How To run
### Run kafka 
#### from docker compose file
 Just run
```console
docker-compose up -d
```
This command automatically start a kafka server running on port 9092
#### Or run kafka locally
- Install kafka locally: check the process [here](http://daringfireball.net/projects/markdown/)
- Once extracted, go to folder and run next command to start zookeper
```console
bin/zookeeper-server-start.sh config/zookeeper.properties
```
- Run next command to start kafka
```console
bin/kafka-server-start.sh config/server.properties
```
### Topic
Kafka topic is created by default for the producer, but if you want to create it manually you can run
```console
bin/kafka-topics.sh --create --topic novice-players --bootstrap-server localhost:9092
```
### Consumer
To consume messages published on the topic run:
```console
bin/kafka-console-consumer.sh --topic novice-players --from-beginning --bootstrap-server localhost:9092
```
### H2 Db
In memory database is included into the project, once the project is running you can access to this [link](http://localhost:8080/h2-console/) to check de UI of the db:
- user: root
- password: root
- JDBC url: jdbc:h2:mem:player_db

### Running app
```console
./gradlew clean build

./gradlew bootRun
```

## Calling API example
```console
curl --location --request POST 'localhost:8080/test' \
--header 'Content-Type: application/json' \
--data-raw '{
    "players": [
        {
            "name": "juan",
            "type": "expert"
        },
        {
            "name": "luis",
            "type": "testType"
        },
        {
            "name": "Pedro pablo",
            "type": "other"
        },
        {
            "name": "Camilo",
            "type": "novice"
        }
    ]
}'
```

## To improve
- Adding metrics using Prometheus and Micrometer
- Adding integration test
- Improve docker-compose file to create a network and run the service as a container
- Adding some CompletableFuture object in order to process each Player parallel, having in mind that depending on player type some operation can be performed in parallel.

## Linkedin
https://www.linkedin.com/in/jgmorah/