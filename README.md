# Fridge Food Manage

A separated frontend/backend fridge ingredient management app.

## Stack

- Frontend: Vue 3, Vite, Vue Router, Pinia, Axios, Vant
- Backend: Spring Boot 2.7.x, Java 8, MyBatis, MySQL
- Database: MySQL 8.x

## Project Layout

```text
frontend/   Vue mobile-first client
backend/    Spring Boot REST API
database/   MySQL schema and seed data
```

## Quick Start

1. Create database and seed data:

```sql
source database/init.sql;
```

2. Update `backend/src/main/resources/application.yml` with your MySQL username and password.

3. Start backend:

```bash
cd backend
mvn spring-boot:run
```

4. Start frontend:

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`, and API calls under `/api` are proxied to `http://localhost:8080`.

## Docker

Run backend and MySQL together:

```bash
docker compose up -d --build
```

The backend is exposed at `http://localhost:8080`. MySQL is exposed on host port `3307` to avoid conflicting with a local MySQL installed on `3306`.

Stop services:

```bash
docker compose down
```

Remove MySQL data and re-run `database/init.sql` on the next start:

```bash
docker compose down -v
```
