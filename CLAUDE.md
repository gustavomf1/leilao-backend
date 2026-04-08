# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Run application
./mvnw spring-boot:run

# Build JAR
./mvnw clean package

# Run all tests
./mvnw test

# Run single test class
./mvnw test -Dtest=ErpleilaoApplicationTests

# Start infrastructure (PostgreSQL, Redis, Evolution API)
docker compose up -d

# Stop infrastructure
docker compose down
```

## Architecture Overview

**Spring Boot 3.5 / Java 21** REST API for cattle auction (leilão) management.

### Package Structure (`backstage.project.erpleilao`)

Standard layered architecture:
- `controller/` — REST endpoints; one controller per domain entity
- `service/` — Business logic; all transactional operations
- `repository/` — Spring Data JPA interfaces
- `entity/` — JPA entities and enums (`TipoUsuario`, etc.)
- `dtos/` — Request/response DTOs (separated from entities)
- `config/` — Spring configuration (Security, Redis, WebSocket, OpenAPI)
- `security/` — JWT filter (`SecurityFilter`) and token service
- `exception/` — Centralized error handling via `RestExceptionHandler`
- `messaging/` — WebSocket + Redis pub/sub (`LoteRedisListener`, `EvolutionApiClient`)

### Domain Model

The system manages cattle auctions:
- `Usuario` — system users with `TipoUsuario` enum (Cliente / Funcionário)
- `Cliente` — auction participants, each has multiple `Fazenda` (farms)
- `Leilao` — auctions with associated `Condicao` (payment conditions) and `TaxaComissao` (commission rates). Possui `StatusLeilao` enum (`ABERTO`, `EM_ANDAMENTO`, `FINALIZADO`) que controla o ciclo de vida do evento.
- `Lote` — auction lots with `vendedor_id` and `comprador_id` FK references to `Cliente`. Possui `StatusLote` enum (`AGUARDANDO_ESCRITORIO`, `AGUARDANDO_LANCE`, `FINALIZADO`). Campo `nao_vendido_no_leilao` (VARCHAR(1) "S"/"N") marca lotes que não receberam lance ao encerrar o evento.

### Security

JWT-based auth via `SecurityFilter` (pre-request filter). Public endpoints are configured in `SecurityConfig`; all others require a valid token. JWT secret is in `application.properties`.

### Infrastructure (Docker Compose)

| Service | Port | Purpose |
|---|---|---|
| PostgreSQL 16 | 5433 (host) / 5432 (container) | Main DB (`prod_leilao`) |
| Redis Alpine | 6380 (host) / 6379 (container) | Caching & lot bid pub/sub |
| Evolution API | 8081 | WhatsApp/messaging integration |
| App | 8080 | Spring Boot app |

### Key Conventions

- DTOs are used for all controller input/output — entities are never exposed directly.
- `application.properties` holds all environment config (DB, JWT secret, Redis, Evolution API URL). No `.env` file.
- Schema is managed via **Flyway** migrations in `src/main/resources/db/migration/` (naming: `V<n>__description.sql`, atualmente em **V17**). Hibernate is set to `ddl-auto=validate` — it verifies the schema but never modifies it.
- Swagger UI available at `/swagger-ui.html` (via SpringDoc OpenAPI 2.8.5).

### Evento de Leilão (Fluxo de Lances)

Funcionalidade que permite executar o evento do leilão em tempo real. Endpoints autenticados:

- `PATCH /api/leiloes/{id}/iniciar` — Inicia o evento (ABERTO → EM_ANDAMENTO). Leilão FINALIZADO **não pode** ser reiniciado. Lotes não são avançados automaticamente — cada lote deve ser avançado manualmente via `PATCH /api/lote/{id}/status/avancar`.
- `PATCH /api/leiloes/{id}/encerrar` — Encerra o evento (EM_ANDAMENTO → FINALIZADO). Lotes sem lance recebem `nao_vendido_no_leilao = "S"`.
- `PATCH /api/lote/{id}/preco` — Registra lance em um lote (AGUARDANDO_LANCE → FINALIZADO).

### Link de Evento para Funcionário (`PublicoLoteController`)

Endpoints **autenticados** (JWT obrigatório) para a tela de anotação de lances enviada a um funcionário externo:

- `GET /api/publico/leilao/{leilaoId}/lotes` — Lista lotes do leilão com status `AGUARDANDO_LANCE` ou `FINALIZADO`.
- `PATCH /api/publico/lote/{id}/preco` — Registra lance. O lance dispara o mesmo fluxo Redis → WebSocket, atualizando todas as telas em tempo real.

O link é gerado pela tela de evento (`/leiloes/{id}/evento`) clicando em **"Gerar Link"** enquanto o leilão está `EM_ANDAMENTO`. O link segue o padrão `{frontendUrl}/#/publico/evento/{leilaoId}`. A rota exige `authGuard` — o funcionário precisa estar logado no sistema para acessá-la. O WebSocket envia o JWT nos `connectHeaders` do STOMP.

### Monitor de Lotes

- Filtro `?naoVendido=true` disponível em `GET /api/lote` para buscar apenas lotes não vendidos.
- Admin pode excluir lotes em qualquer status.

Ver `docs/evento-leilao.md` e `docs/link-publico.md` para documentação completa.
