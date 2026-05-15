# Payment Gateway

API REST que actúa como orquestador central de pagos para el ecosistema EventsProject (UPTC). Recibe solicitudes de cobro, valida el comercio, identifica la franquicia (Visa o Mastercard), se comunica con el servicio correspondiente y registra el resultado. Incluye un módulo de Tesorería para consulta y liquidación de transacciones.

## Requisitos

- Java 21
- Maven (incluido via wrapper `mvnw`)
- Docker y Docker Compose

## Variables de entorno

Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

```env
DB_NAME=payment_gateway_db
DB_USER=admin
DB_PASSWORD=admin123
DB_PORT_EXTERNAL=5435
DB_PORT_INTERNAL=5432
DB_HOST=localhost
```

## Levantar la base de datos con Docker

```bash
docker compose up -d
```

Esto levanta un contenedor PostgreSQL con la configuración del `.env`. Las tablas y datos iniciales se crean automáticamente mediante Flyway al arrancar la aplicación.

## Configuración local

Crea el archivo `src/main/resources/application-local.yaml` con los valores de conexión que coincidan con tu `.env`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5435/payment_gateway_db
    username: admin
    password: admin123
```

## Ejecutar la aplicación

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

En Windows:

```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

La aplicación quedará disponible en `http://localhost:8080`.

## Documentación de la API

Una vez levantada la aplicación, la documentación interactiva generada por Swagger está disponible en:

http://localhost:8080/swagger-ui.html

Desde ahí puedes explorar y probar todos los endpoints disponibles sin necesidad de herramientas externas.

## Servicios externos requeridos

La pasarela depende de dos servicios de franquicias que deben estar corriendo antes de procesar pagos:

| Servicio    | Puerto |
|-------------|--------|
| Visa        | 3001   |
| Mastercard  | 3002   |

## Endpoints principales

| Método | Ruta                        | Descripción                              |
|--------|-----------------------------|------------------------------------------|
| POST   | `/payment-gateway`          | Procesa un pago                          |
| GET    | `/treasury/transactions`    | Consulta transacciones no liquidadas     |
| POST   | `/treasury/liquidate`       | Liquida una lista de transacciones       |