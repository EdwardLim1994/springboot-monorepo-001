# Spring Federation Lab

Learning monorepo: Spring Boot GraphQL subgraphs, Apollo Router federation, Kafka events, and one Postgres database per service.

## Run

Requires JDK 25+, Gradle 8.14+ or 9+, and Docker Compose.

Run the Spring apps on the host while Docker runs Postgres, Kafka, and Apollo Router:

```sh
gradle runApps
```

Stop the infrastructure with `docker compose down`. The apps run in the foreground; press Ctrl+C to stop them.

Apply database migrations for both services:

```sh
gradle migrateAll
```

Both databases must be reachable at their configured datasource URLs. The service-specific tasks are `:services:products:migrate` and `:services:orders:migrate`.

```sh
docker compose up --build
```

Apollo Sandbox: http://localhost:4000. GraphQL endpoint: http://localhost:4000/graphql. GraphiQL is available at http://localhost:8081/graphiql for products and http://localhost:8082/graphiql for orders when running the apps on the host.

Create an order through the router:

```graphql
mutation {
  createOrder(productId: "book-1", quantity: 2) {
    id
    product { id name price }
  }
}
```

The event is published to Kafka topic `order-created` after the order is saved, then logged by the orders consumer. Create products first:

```graphql
mutation {
  createProduct(id: "book-1", name: "Learning GraphQL", price: 19.99) {
    id
    name
    price
  }
}
```

Orders creates `order-created` on startup. Containerized apps use Kafka at `kafka:9092`; host-run apps use `localhost:9094`.

## Run one service and its database

```sh
docker compose -f services/products/docker-compose.yml up -d
gradle :services:products:bootRun
```

Products DB publishes localhost:5433; orders DB publishes localhost:5434:

```sh
docker compose -f services/orders/docker-compose.yml up -d
gradle :services:orders:bootRun
```

For local `bootRun`, Kafka must be available at `localhost:9092` (`docker compose up kafka -d` from root). To run standalone services together, use root compose; it owns both service databases. Avoid starting a service-level database and root stack together because both expose different host ports and have separate data volumes.

## Layout

```text
services/products   Product subgraph and products database compose
services/orders     Order subgraph, Kafka producer/consumer, orders database compose
common/federation   Shared Apollo Federation schema configuration
router/             Apollo Router and composed supergraph schema
docker-compose.yml  Router, Kafka, services, and their databases
```

This is a learning scaffold: credentials are local demo values, Liquibase manages schemas, events are simple CSV strings, and Kafka publishing is not transactionally coupled to the database write.
