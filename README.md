# Getting Started

### Prerequisites
- Docker and Docker Compose installed
- Git installed

### Cloning the Repository

1. Clone the repository:
```bash
git clone https://github.com/zanfa97/challenge-infocube.git
cd challenge-infocube
```

### Running with Docker (Recommended)

1. Build and run the application:
```bash
docker-compose up -d
```

2. Access the API:
- API endpoint: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

### Stopping the Application

To stop all services:
```bash
docker-compose down
```

To remove volumes as well:
```bash
docker-compose down -v
```

***

## Tecnologie Utilizzate

### Spring Web
- REST API con `@RestController` e `@RequestMapping`
- Gestione HTTP (GET, POST, PUT, DELETE)
- Documentazione API con OpenAPI/Swagger

### Spring Data JPA
- ORM per persistenza dati
- Pattern Repository
- Derived Query e JPQL
- Relazioni tra entità
- MySQL come DBMS

### MapStruct
- Mapping entità-DTO
- Generazione automatica mapping
- Integrazione Spring
- Supporto mapping complesso

### Flyway
- Migrazioni database
- Versionamento schemi
- Controllo integrità

### Maven
- Gestione dipendenze
- Build e ciclo di vita
- Configurazione pom.xml

### Lombok
- Riduzione boilerplate
- Annotazioni per getter/setter/costruttori
