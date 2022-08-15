# Kafka Streams Basic
This is a very basic application implemented to play with Kafka Streams.

Main Topic accepts students to register a school between 7-14 year old.
Processor processes register list and forwards Students either to Middle School or to High School.



### Installing
1. Clone this repository anywhere on your machine:
```
git clone git@github.com:gizyyy/kafka-streams.git
```

2. Run docker compose build
```
docker-compose up -d --build
```

## Installing dependencies
```bash
./gradlew build
```

## Tests and checks
To run all tests:
```bash
./gradlew test
```

