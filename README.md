# Microservicio de Transacciones con Anti-Fraude

Este proyecto implementa un microservicio para la gestión de transacciones financieras con validación anti-fraude, utilizando Java, Spring Boot, PostgreSQL y Apache Kafka.

## Características

- Creación de transacciones financieras
- Validación automática de transacciones mediante reglas de negocio
- Actualización asíncrona del estado de las transacciones
- Consulta de transacciones por ID externo

## Reglas de negocio

- Toda transacción con un valor **mayor a 1000** será automáticamente **rechazada**.
- Las transacciones pasan por tres estados posibles: **Pendiente**, **Aprobado** y **Rechazado**.

## Flujo del proceso

1. El servicio recibe una solicitud para crear una transacción
2. La transacción se guarda en la base de datos con estado "Pendiente"
3. Se envía un evento a Kafka para notificar la creación de la transacción
4. El servicio Anti-Fraude consume el evento y valida la transacción
5. Anti-Fraude envía un evento con el resultado de la validación (Aprobado/Rechazado)
6. El servicio de transacciones actualiza el estado de la transacción en la base de datos

## Requisitos previos

- JDK 17 o superior
- Maven 3.6 o superior
- Docker y Docker Compose

## Configuración del entorno

El proyecto incluye un archivo `docker-compose.yml` para configurar los servicios requeridos:

```bash
# Iniciar los servicios
docker-compose up -d

# Verificar que los servicios estén corriendo
docker-compose ps
```

## Compilación y ejecución

```bash
# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
java -jar target/transactions-service-0.0.1-SNAPSHOT.jar
```

Alternativamente, puede ejecutar la aplicación directamente con Maven:

```bash
mvn spring-boot:run
```

## API REST

### Crear una transacción

```
POST /transactions
```

**Cuerpo de la solicitud:**
```json
{
  "accountExternalIdDebit": "GUID",
  "accountExternalIdCredit": "GUID",
  "tranferTypeId": 1,
  "value": 120
}
```

**Respuesta:**
```json
{
  "transactionExternalId": "GUID",
  "transactionType": {
    "name": "Transfer"
  },
  "transactionStatus": {
    "name": "Pending"
  },
  "value": 120,
  "createdAt": "2024-03-24T10:20:30Z"
}
```

### Consultar una transacción

```
GET /transactions/{transactionExternalId}
```

**Respuesta:**
```json
{
  "transactionExternalId": "GUID",
  "transactionType": {
    "name": "Transfer"
  },
  "transactionStatus": {
    "name": "Approved"
  },
  "value": 120,
  "createdAt": "2024-03-24T10:20:30Z"
}
```

## Pruebas

Para ejecutar las pruebas unitarias:

```bash
mvn test
```

## Arquitectura

La solución sigue una arquitectura basada en microservicios con los siguientes componentes:

- **API REST**: Expone los endpoints para crear y consultar transacciones
- **Servicio de Transacciones**: Gestiona la lógica de negocio para las transacciones
- **Servicio Anti-Fraude**: Valida las transacciones según las reglas de negocio
- **Kafka**: Sistema de mensajería para comunicación asíncrona entre componentes
- **PostgreSQL**: Base de datos relacional para almacenar las transacciones

## Consideraciones de Escalabilidad y Concurrencia

Para abordar escenarios con alto volumen de operaciones, se han implementado las siguientes estrategias:

1. **Procesamiento asíncrono**: Utilizando Kafka para desacoplar la creación y validación de transacciones
2. **Transacciones idempotentes**: Garantizando que operaciones duplicadas no generen efectos no deseados
3. **Índices optimizados**: Facilitando la búsqueda rápida de transacciones

Para más detalles sobre estrategias de escalabilidad, consultar el documento `ESCALABILIDAD.md`.

## Posibles mejoras

- Implementación de caché para mejorar el rendimiento de las consultas frecuentes
- Particionamiento de la base de datos para manejar grandes volúmenes de datos
- Implementación de un sistema de monitoreo y alertas
- Configuración de Circuit Breakers para mayor resiliencia