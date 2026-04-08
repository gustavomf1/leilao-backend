# Link Público do Evento de Leilão

## Visão Geral

Quando um leilão está `EM_ANDAMENTO`, o operador pode gerar um link público e enviá-lo para um responsável externo (por exemplo, um anotador de lances em campo). Esse link abre uma página **sem necessidade de login** que exibe os lotes em tempo real e permite registrar o valor do lance.

Qualquer lance registrado nessa página é refletido instantaneamente na tela principal do evento via WebSocket.

---

## Fluxo Completo

```
Operador clica "Gerar Link"
        │
        ▼
URL copiada: http://localhost:4200/#/publico/evento/{leilaoId}
        │
        ▼
Responsável abre o link (sem login)
        │
        ├── GET /api/publico/leilao/{id}/lotes
        │   └── Retorna lotes com status AGUARDANDO_LANCE ou FINALIZADO
        │
        ├── WebSocket /ws-leilao → /topic/lotes (tempo real)
        │
        └── Responsável preenche preço e confirma
                │
                ▼
        PATCH /api/publico/lote/{id}/preco
                │
                ▼
        Backend: status AGUARDANDO_LANCE → FINALIZADO
                │
                ▼
        Redis pub/sub → STOMP /topic/lotes
                │
         ┌──────┴───────┐
         ▼              ▼
  Página pública   Tela do evento
  atualiza o card  atualiza stats,
  para FINALIZADO  progresso e tabelas
```

---

## Endpoints Públicos

Mapeados em `PublicoLoteController` — sem `@RequirePermission`, liberados via `SecurityConfig`:

```java
req.requestMatchers(HttpMethod.GET,   "/api/publico/**").permitAll();
req.requestMatchers(HttpMethod.PATCH, "/api/publico/**").permitAll();
```

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/publico/leilao/{leilaoId}/lotes` | Lista lotes AGUARDANDO_LANCE e FINALIZADO |
| PATCH | `/api/publico/lote/{id}/preco` | Registra lance (body: `{ "precoCompra": 1500.00 }`) |

---

## Regras de Negócio

- A página pública exibe **apenas** lotes com status `AGUARDANDO_LANCE` ou `FINALIZADO`.
- Lotes `AGUARDANDO_ESCRITORIO` não aparecem na página pública.
- Um lance só pode ser registrado se o lote estiver em `AGUARDANDO_LANCE` — o backend valida via `LoteService.registrarPreco`.
- O link é válido enquanto o leilão estiver `EM_ANDAMENTO`. Após o encerramento, os lotes ainda aparecem (todos `FINALIZADO`), mas não é possível registrar novos lances.

---

## Filtros na Página Pública

A tela possui 3 tabs de filtro:

| Tab | Exibe |
|-----|-------|
| Todos | AGUARDANDO_LANCE + FINALIZADO |
| Aguardando Lance | Apenas AGUARDANDO_LANCE |
| Finalizado | Apenas FINALIZADO |

---

## Arquivos Relevantes

### Backend
- `controller/PublicoLoteController.java` — endpoints sem auth
- `service/LoteService.java` → `listarPorLeilaoPublico()`, `registrarPreco()`
- `repository/LoteRepository.java` → `findByLeilaoIdAndStatusIn()`
- `config/SecurityConfig.java` → regras `permitAll` para `/api/publico/**`

### Frontend
- `components/leilao/evento-publico/evento-publico.component.ts` — tela pública
- `components/leilao/evento-leilao/evento-leilao.component.ts` — botão "Gerar Link" + WebSocket
- `core/services/lote.service.ts` → `listarPorLeilaoPublico()`, `registrarPrecoPublico()`
- `core/services/lote-websocket.service.ts` — conexão STOMP (funciona sem token)
- `app.routes.ts` — rota `publico/evento/:leilaoId` sem `authGuard`
