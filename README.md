# Parliament Voting API

Parlamenti szavazásokat kezelő REST API alkalmazás, amely Java és Spring Boot használatával készült.

## Felhasznált technológiák

- Java 21
- Spring Boot
- Gradle
- Spring Data JPA / Hibernate
- H2 Database
- Docker

## Futtatás

Gradle használatával:

```powershell
.\gradlew.bat bootRun
```

Docker használatával:

```powershell
docker build -t parlament-api .
docker run -p 8080:8080 parlament-api
```

Az alkalmazás a http://localhost:8080 címen érhető el.