# Place Service

> (Language portuguese) API para gerenciar lugares (CRUD) que faz parte [desse desafio](https://github.com/RocketBus/quero-ser-clickbus/tree/master/testes/backend-developer) para pessoas desenvolvedoras backend que se candidatam para a ClickBus.

![tag desafio](https://img.shields.io/static/v1?label=By&message=Rudolf_HiOk&color=8257E5&labelColor=000000)
![tag desafio](https://img.shields.io/static/v1?label=Type&message=Challenge&color=8257E5&labelColor=000000)

## Technologies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Spring Data + R2DBC](https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- [Slugify](https://github.com/slugify/slugify)

## How to run

### Local
- Clone repository (git)
- Build project:
```
./mvnw clean package
```
- Run:
```
java -jar place-service/target/place-service-0.0.1-SNAPSHOT.jar
```
API access URL: [localhost:8080](http://localhost:8080).

### Docker

- Clone repository (git)
- Build project:
```
./mvnw clean package
```
- Image build:
```
./mvnw spring-boot:build-image
```
- Container run:
```
docker run --name place-service -p 8080:8080 -d place-service:0.0.1-SNAPSHOT
```

API access URL: [localhost:8080](http://localhost:8080).

## API Endpoints

Make HTTP request with [httpie](https://httpie.io) tool:

- POST /places
```
http POST :8080/places name="Place" state="State"

HTTP/1.1 200 OK
Content-Length: 129
Content-Type: application/json

{
    "createdAt": "2023-04-20T19:00:07.241632",
    "name": "Place",
    "slug": "place",
    "state": "State",
    "updatedAt": "2023-04-20T19:00:07.241632"
}
```
