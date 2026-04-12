# Evento de Leilao - Documentacao

## Visao Geral

Funcionalidade que permite executar o evento do leilao em tempo real, registrando lances para cada lote e controlando o ciclo de vida do leilao.

---

## Ciclo de Vida do Leilao (StatusLeilao)

```
ABERTO  ──────>  EM_ANDAMENTO  ──────>  FINALIZADO
(criado)        (evento ativo)         (encerrado)
```

| Status | Descricao | Acoes disponiveis |
|--------|-----------|-------------------|
| **ABERTO** | Leilao recem-criado, aguardando inicio | Botao "Comecar Leilao" |
| **EM_ANDAMENTO** | Evento ativo, lotes abertos para lances | Registrar lances, Botao "Encerrar Leilao" |
| **FINALIZADO** | Evento encerrado, sem retorno | Apenas visualizacao do resultado |

> O status eh irreversivel. Uma vez finalizado, nao pode ser reiniciado.

---

## Ciclo de Vida do Lote (StatusLote)

```
AGUARDANDO_ESCRITORIO  ──>  AGUARDANDO_LANCE  ──>  FINALIZADO
                            (ao iniciar leilao)    (ao registrar lance)
```

### Flag `nao_vendido_no_leilao`

- Tipo: `VARCHAR(1)` - `"S"` ou `"N"`
- Default: `"N"` (ao criar o lote)
- Marcado como `"S"` automaticamente ao **encerrar o leilao** para lotes que permaneceram em `AGUARDANDO_LANCE` sem receber lance
- Visivel na listagem de lotes como badge vermelho "Nao vendido"

---

## Fluxo Passo a Passo

### 1. Acessar o Evento

**Opcao A** - Pela lista de leiloes:
1. Menu lateral > **Leiloes**
2. Na tabela, clique no icone de martelo verde na coluna "Acoes"

**Opcao B** - Pela visualizacao do leilao:
1. Menu lateral > **Leiloes** > clique no icone de olho para ver detalhes
2. No header, clique no botao verde **"Evento"**

Ambos levam para a rota: `/leiloes/:id/evento`

### 2. Iniciar o Leilao

1. Na tela do evento, o status aparece como **"ABERTO"** (badge azul)
2. Clique no botao verde **"Comecar Leilao"**
3. Um modal de confirmacao aparece com botao **"Iniciar"** (verde)
4. Ao confirmar:
   - O status do leilao muda para **EM_ANDAMENTO**
   - Todos os lotes em `AGUARDANDO_ESCRITORIO` avancam automaticamente para `AGUARDANDO_LANCE`
   - A flag `nao_vendido_no_leilao` eh resetada para `"N"` em todos os lotes
   - A tabela de lotes com input de lance aparece

### 3. Registrar Lances

1. Na tabela "Lotes Aguardando Lance", cada lote tem um campo de valor
2. Digite o valor do lance no campo numerico
3. Clique no botao verde **"Confirmar"**
4. Modal de confirmacao mostra o valor e o codigo do lote
5. Ao confirmar:
   - O `precoCompra` do lote eh salvo
   - O status do lote muda para `FINALIZADO`
   - O lote sai da tabela "Aguardando Lance" e vai para "Lotes Arrematados"
   - Os KPIs e a barra de progresso atualizam automaticamente

### 4. Encerrar o Leilao

1. Clique no botao vermelho **"Encerrar Leilao"**
2. O modal informa quantos lotes ficaram sem lance (se houver)
3. Ao confirmar:
   - O status do leilao muda para **FINALIZADO**
   - Lotes que ficaram em `AGUARDANDO_LANCE` sem preco recebem `nao_vendido_no_leilao = "S"`
   - O botao "Comecar" e "Encerrar" desaparecem
   - A tela mostra o resultado final com mensagem "Este leilao foi encerrado"

---

## API Endpoints

### Iniciar Evento
```
PATCH /api/leiloes/{id}/iniciar
```
- **Permissao:** LEILOES:EDITAR
- **Pre-condicao:** Status do leilao deve ser `ABERTO`
- **Efeito:** Status -> `EM_ANDAMENTO`, lotes avancam para `AGUARDANDO_LANCE`

### Encerrar Evento
```
PATCH /api/leiloes/{id}/encerrar
```
- **Permissao:** LEILOES:EDITAR
- **Pre-condicao:** Status do leilao deve ser `EM_ANDAMENTO`
- **Efeito:** Status -> `FINALIZADO`, lotes sem lance recebem flag `nao_vendido_no_leilao = "S"`

### Registrar Lance (endpoint existente)
```
PATCH /api/lote/{id}/preco
Body: { "precoCompra": 5500.00 }
```
- **Permissao:** LOTES:EDITAR
- **Pre-condicao:** Status do lote deve ser `AGUARDANDO_LANCE`
- **Efeito:** Salva preco, status do lote -> `FINALIZADO`

---

## Tela do Evento - Elementos

| Elemento | Descricao |
|----------|-----------|
| **Badge de status** | Mostra ABERTO (azul), EM ANDAMENTO (verde) ou FINALIZADO (escuro) |
| **KPI Cards** | Total de Lotes, Aguardando Lance, Finalizados, Progresso (%) |
| **Barra de progresso** | Visual do andamento (finalizados / total) |
| **Tabela Aguardando Lance** | Lotes com input de valor + botao confirmar (so aparece em EM_ANDAMENTO) |
| **Tabela Arrematados** | Lotes finalizados com valor do lance (so aparece em EM_ANDAMENTO) |
| **Tabela Geral** | Todos os lotes com status e flag "Nao Vendido" (aparece em ABERTO e FINALIZADO) |

---

## Migrations

- **V16** — Adiciona `nao_vendido_no_leilao VARCHAR(1) DEFAULT 'N'` na tabela `lotes` e `lei_em_andamento VARCHAR(1)` na tabela `leilao`
- **V17** — Substitui `lei_em_andamento` por `lei_status VARCHAR(20) DEFAULT 'ABERTO'` na tabela `leilao`

---

## Arquivos Modificados/Criados

### Backend (`erpleilao`)
| Arquivo | Tipo |
|---------|------|
| `entity/enums/StatusLeilao.java` | Novo |
| `entity/LeilaoEntity.java` | Modificado - campo `status` (StatusLeilao) |
| `entity/LoteEntity.java` | Modificado - campo `naoVendidoNoLeilao` |
| `dtos/LoteDisplayDTO.java` | Modificado - inclui `naoVendidoNoLeilao` |
| `service/LeilaoService.java` | Modificado - `iniciarLeilao()`, `encerrarLeilao()` |
| `service/LoteService.java` | Modificado - default `"N"` no cadastrar |
| `controller/LeilaoController.java` | Modificado - endpoints `/iniciar` e `/encerrar` |
| `db/migration/V16__*.sql` | Novo |
| `db/migration/V17__*.sql` | Novo |

### Frontend (`leilao-frontend`)
| Arquivo | Tipo |
|---------|------|
| `core/models/entities.model.ts` | Modificado - `StatusLeilao`, `naoVendidoNoLeilao` |
| `core/services/leilao.service.ts` | Modificado - novos metodos |
| `shared/services/alert.service.ts` | Modificado - confirm customizavel |
| `shared/components/confirm-modal.component.ts` | Modificado - label/cor dinamicos |
| `components/leilao/evento-leilao/*` | Novo - componente completo |
| `components/leilao/leilao-list/*` | Modificado - botao evento |
| `components/leilao/leilao-view/*` | Modificado - botao evento |
| `components/lote/lote-monitor/*` | Modificado - badge "Nao vendido" |
| `app.routes.ts` | Modificado - rota `/leiloes/:id/evento` |
