# svc-leakage

Spring Boot 3.3.14 microservice serving the leakage workflow APIs.

## Build & Run (Maven)
- Build: `mvn -q clean package`
- Run: `mvn -q spring-boot:run`
- Jar: `java -jar target/svc-leakage-0.0.1-SNAPSHOT.jar`

## Build Docker
docker build -t svc-leakage .

## Endpoints
- POST `/api/requests` (maker)
- GET `/api/requests/pending` (approver, super_approver)
- POST `/api/requests/{id}/approve` (role-based)
- POST `/api/requests/{id}/reject` (role-based)
- POST `/api/requests/{id}/escalate` (approver only)
- GET `/api/requests/history` (all roles)
- POST `/api/requests/{id}/send-email` (maker)

Headers required:
- `x-user-id`: user identifier
- `x-user-role`: one of `maker`, `approver`, `super_approver` 