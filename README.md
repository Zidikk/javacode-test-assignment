# javacode-test-assignment

## Описание

Это приложение предназначено для управления кошельками с использованием REST API. Оно использует Spring Boot, PostgreSQL и Liquibase для управления состоянием базы данных.

## Стек технологий

- Java 17
- Spring Boot 3.3.5
- PostgreSQL
- Liquibase
- Docker
- Docker Compose

## Установка

### Требования

- Установленный [Docker](https://www.docker.com/get-started)
- Установленный [Docker Compose](https://docs.docker.com/compose/install/)

### Клонирование репозитория

```bash
git clone https://github.com/yourusername/javacode-test-assignment.git
cd javacode-test-assignment
```

## Настройка переменных окружения
Переменные окружения для подключения к базе данных и настройки приложения можно установить в файле docker-compose.yml. 
```yaml
services:
  app:
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/javacode_db
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: pass
      SPRING_APPLICATION_NAME: javacode-test-assignment
      SPRING_LIQUIBASE_CHANGELOG: classpath:db/changelog/db.changelog-master.xml
  db:
    environment:
      POSTGRES_DB: javacode_db
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: pass
```

## Запуск приложения
Для запуска приложения используйте Docker Compose. Это создаст и запустит необходимые контейнеры.
```bash
docker-compose up --build
```
После этого приложение будет доступно по адресу http://localhost:8080.

# Структура API
## Создание и выполнение операций с кошельком
POST /api/v1/wallet

Тело запроса:
```json
{
"walletId": "UUID",
"operationType": "DEPOSIT" or "WITHDRAW",
"amount": 1000
}
```
## Получение информации о кошельке
GET /api/v1/wallets/{walletId}

Параметры:

- walletId: UUID кошелька

# Миграции базы данных
Приложение использует Liquibase для управления миграциями базы данных. Все миграции хранятся в директории db/changelog. 
При запуске приложения Liquibase автоматически применит все новые изменения к базе данных.