# Project 1: Spring Boot Inventory Service

A real-world inventory backend with product, warehouse and stock-movement modules.

## Scope
- Product CRUD
- Warehouse CRUD
- Inventory by product + warehouse
- Stock IN / OUT
- Prevent negative stock
- Transactional stock updates
- Optimistic locking on inventory
- Validation and centralized exception handling
- Unit + integration tests
- PostgreSQL persistence
- OpenAPI / Swagger UI
- Basic authentication for business APIs

## Tech Stack
- Java 17
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- JUnit + Mockito
- H2 for tests
- OpenAPI / Swagger UI

## Requirements
1. JDK 17+
2. Maven 3.9+
3. PostgreSQL 14+

## Database setup
Create a database:
```sql
CREATE DATABASE inventory_db;
```

Default local configuration:
- DB URL: jdbc:postgresql://localhost:5432/inventory_db
- DB user: postgres
- DB password: postgres
- API username: admin
- API password: admin123

For a real deployment, change the credentials through environment variables.

## Run
```bash
mvn clean test
mvn spring-boot:run
```

Or:
```bash
mvn clean package
java -jar target/inventory-service-1.0.0.jar
```

## Swagger
Open:
http://localhost:8080/swagger-ui.html

Swagger and health are public. Other APIs use Basic Auth:
- username: admin
- password: admin123

## API examples

### Create product
POST `/api/products`
```json
{
  "sku": "LAPTOP-001",
  "name": "Business Laptop",
  "description": "15 inch laptop",
  "price": 65000,
  "reorderLevel": 10,
  "active": true
}
```

### Create warehouse
POST `/api/warehouses`
```json
{
  "code": "WH-DEL",
  "name": "Delhi Warehouse",
  "location": "New Delhi"
}
```

### Stock IN
POST `/api/stock-movements`
```json
{
  "productId": 1,
  "warehouseId": 1,
  "type": "IN",
  "quantity": 50,
  "reference": "PO-1001",
  "reason": "Initial purchase"
}
```

### Stock OUT
POST `/api/stock-movements`
```json
{
  "productId": 1,
  "warehouseId": 1,
  "type": "OUT",
  "quantity": 5,
  "reference": "SO-1001",
  "reason": "Customer order"
}
```

### Inventory
GET `/api/inventory`

### Movement history
GET `/api/stock-movements`

## Architecture
Controller -> Service -> Repository -> PostgreSQL

The stock movement operation is transactional:
1. Load/create the product-warehouse inventory row.
2. Calculate the new quantity.
3. Reject an OUT movement if stock would become negative.
4. Update inventory.
5. Save an immutable movement record in the same transaction.

## Success metrics
- No negative stock.
- Duplicate SKU and warehouse codes rejected.
- Invalid request data rejected with clear validation messages.
- Stock update and movement audit record succeed together.
- Tests pass before delivery.

## Suggested demo flow
1. Start PostgreSQL.
2. Run the application.
3. Open Swagger.
4. Create one product and one warehouse.
5. Add 50 units.
6. Check inventory.
7. Remove 10 units.
8. Try removing 100 units and show the validation/business error.
9. Open movement history.
10. Run `mvn clean test` and show passing tests.
