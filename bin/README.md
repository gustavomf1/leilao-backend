# 🐄 Sistema de Gestão de Leilões 

Este repositório contém a modelagem de dados para um sistema de leilões, focado na rastreabilidade de lotes, gestão de clientes (compradores/vendedores) e propriedades rurais.

---

##  Estrutura do Banco de Dados

### 1. Usuários e Acessos
Armazena as credenciais de acesso ao sistema.
* **Usuarios**: `id`, `nome`, `email`, `senha`, `cpf`

### 2. Núcleo de Negócio (Clientes e Fazendas)
Gerencia os participantes e suas respectivas propriedades.
* **Cliente**: `id`, `nome`, `cpf`, `telefone`, `cidade`, `uf`, `rg`, `fazenda_id`
* **Fazenda**: `id`, `inscricao`, `nome`, `uf`, `cidade`, `cnpj`, `titular_id`

### 3. Configuração do Evento (Leilão)
Define as regras, taxas e localização de cada evento.
* **Leilao**: `id`, `local`, `uf`, `cidade`, `descricao`, `data`, `condicoes_id`, `taxas_id`
* **Condicoes**: `id`, `tipo`, `descricao`
* **Taxas**: `id`, `porcentagem`, `tipo_cliente`

### 4. Movimentação (Lotes)
Onde ocorre a transação comercial dos animais.
* **Lote**: `id`, `codigo`, `qntd_animais`, `sexo`, `idade_em_meses`, `peso`, `raca`, `especie`, `categoria_animal`, `obs`, `leilao_id`, `vendedor_id`, `comprador_id`, `preco_compra`

---

## 🧬 Relacionamentos

Abaixo está a representação visual das chaves estrangeiras e conexões entre as entidades:

```mermaid
erDiagram
    CLIENTE ||--o{ FAZENDA : "possui"
    CLIENTE ||--o{ LOTE : "vende (vendedor_id)"
    CLIENTE ||--o{ LOTE : "compra (comprador_id)"
    LEILAO ||--o{ LOTE : "contém"
    LEILAO ||--|| CONDICOES : "possui"
    LEILAO ||--|| TAXAS : "aplica"
