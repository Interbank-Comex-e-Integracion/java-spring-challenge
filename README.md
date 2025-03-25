# Reto de Programación en Java con Spring 🚀

Este reto técnico nos permitirá conocer tus habilidades de desarrollo en Java y Spring Boot.  
La idea es que te diviertas programando mientras demuestras tu enfoque para resolver problemas, buenas prácticas y diseño de código 😄.

> 📌 La forma correcta de enviar tu solución es mediante un **Pull Request (PR)** a este repositorio.

---

## 🧩 Problema

Cada vez que se crea una transacción financiera, esta debe ser validada por nuestro microservicio de antifraude (**Anti-Fraud**). Luego, el mismo servicio envía un mensaje para actualizar el estado de la transacción.

Por ahora, manejamos únicamente **tres estados**:

1. **Pendiente**
2. **Aprobado**
3. **Rechazado**

📌 *Regla de negocio*: Toda transacción con un valor **mayor a 1000** debe ser automáticamente **rechazada**.

### Flujo general:

```mermaid
flowchart LR
  Transaction -- 1.) Guarda operación con estado pendiente --> transactionDatabase[(Database)]
  Transaction -- 2.) Envía evento de registro de operación --> Anti-Fraud
  Anti-Fraud -- 3.a) Envía evento de cambio de estado de operación a aprobado --> Transaction
  Anti-Fraud -- 3.b) Envía evento de cambio de estado de operación a rechazado --> Transaction
  Transaction -- 4.) Actualiza operación con el estado recibido por Anti-Fraud --> transactionDatabase[(Database)]
```

## 🛠️ Stack Tecnológico

Este reto debe ser resuelto utilizando las siguientes tecnologías:

- Java + Spring Boot
- PostgreSQL como base de datos
- Apache Kafka como sistema de mensajería

> 🐳 Se incluye un `docker-compose.yml` para que puedas configurar tu entorno de desarrollo fácilmente.

---

## 🎯 Endpoints esperados

Deberías implementar dos recursos a nivel de API:

### 1. Crear operación

**POST /transactions**

```json
{
  "accountExternalIdDebit": "GUID",
  "accountExternalIdCredit": "GUID",
  "tranferTypeId": 1,
  "value": 120
}
```

### 2. Consultar operación

**GET /transactions/{transactionExternalId}**

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

## 🚀 Escenario opcional (Bonus)

Puedes elegir cualquier enfoque para almacenar las transacciones. Sin embargo, ten en cuenta que podríamos enfrentar escenarios con **alto volumen de operaciones**, tanto en lectura como en escritura simultánea.

**Pregunta opcional:** ¿Cómo abordarías este requisito de escalabilidad y concurrencia?

---

## 📬 Envío de tu solución

1. Haz un **fork** de este repositorio.
2. Realiza tu implementación en tu fork.
3. Abre una **Pull Request (PR)** a este repositorio con tu solución.

No hay limitaciones estrictas en cuanto a la arquitectura o estilo de código. Siéntete libre de aplicar el paradigma, patrones y modularización que consideres más adecuados para resolver el problema.

---

¿Tienes dudas?  
No dudes en contactarnos. ¡Mucho éxito y a divertirse programando! 💪😎