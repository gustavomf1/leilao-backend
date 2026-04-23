-- Baseline: schema existente antes da adoção do Flyway
-- Este script NÃO será executado em bancos já existentes (baseline-on-migrate=true)
-- Serve como referência do schema inicial e para criação de ambientes novos.

CREATE TABLE IF NOT EXISTS usuarios (
    usu_id          BIGSERIAL PRIMARY KEY,
    usu_nome        VARCHAR(255) NOT NULL,
    usu_email       VARCHAR(255) NOT NULL UNIQUE,
    usu_senha       VARCHAR(255),
    usu_cpf         VARCHAR(255) NOT NULL UNIQUE,
    usu_tipo        VARCHAR(50),
    usu_inativo     VARCHAR(1)   NOT NULL DEFAULT 'N',
    usu_telefone    VARCHAR(255),
    usu_cidade      VARCHAR(255),
    usu_uf          VARCHAR(255),
    usu_rg          VARCHAR(255),
    usu_dt_criacao  TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS condicoes (
    con_id                    BIGSERIAL PRIMARY KEY,
    con_descricao             VARCHAR(255) NOT NULL,
    con_captacao              INTEGER,
    con_parcelas              INTEGER,
    con_qtd_dias              INTEGER,
    con_percentual_desconto   NUMERIC(5, 2),
    con_com_entrada           VARCHAR(1)   DEFAULT 'N',
    con_mesmo_dia             VARCHAR(1)   DEFAULT 'N',
    con_tipo_condicao         VARCHAR(20),
    con_aceite_integrado      VARCHAR(40)  DEFAULT 'NORMAL',
    con_inativo               VARCHAR(1)   NOT NULL DEFAULT 'N'
);

CREATE TABLE IF NOT EXISTS taxas_comissao (
    tax_id           BIGSERIAL PRIMARY KEY,
    tax_porcentagem  NUMERIC(5, 2) NOT NULL,
    tax_tipo_cliente VARCHAR(255)  NOT NULL,
    tax_inativo      VARCHAR(1)    NOT NULL DEFAULT 'N'
);

CREATE TABLE IF NOT EXISTS fazendas (
    faz_id        BIGSERIAL PRIMARY KEY,
    faz_nome      VARCHAR(255) NOT NULL,
    faz_inscricao VARCHAR(255),
    faz_cnpj      VARCHAR(255),
    faz_uf        VARCHAR(2),
    faz_cidade    VARCHAR(255),
    faz_dt_criacao TIMESTAMP,
    faz_inativo   VARCHAR(1)   NOT NULL DEFAULT 'N',
    titular_id    BIGINT REFERENCES usuarios (usu_id)
);

CREATE TABLE IF NOT EXISTS leilao (
    lei_id        BIGSERIAL PRIMARY KEY,
    lei_local     VARCHAR(100) NOT NULL,
    lei_uf        VARCHAR(2)   NOT NULL,
    lei_cidade    VARCHAR(100) NOT NULL,
    lei_descricao VARCHAR(255) NOT NULL,
    lei_data      DATE         NOT NULL,
    lei_inativo   VARCHAR(1)   NOT NULL DEFAULT 'N',
    lei_con_id    BIGINT       NOT NULL REFERENCES condicoes (con_id),
    lei_tax_id    BIGINT       NOT NULL REFERENCES taxas_comissao (tax_id)
);

CREATE TABLE IF NOT EXISTS lotes (
    id               BIGSERIAL PRIMARY KEY,
    codigo           VARCHAR(255),
    qntd_animais     INTEGER,
    sexo             VARCHAR(255),
    idade_em_meses   INTEGER,
    peso             DOUBLE PRECISION,
    raca             VARCHAR(255),
    especie          VARCHAR(255),
    categoria_animal VARCHAR(255),
    obs_vaca_parida  VARCHAR(255),
    obs              VARCHAR(255),
    preco_compra     NUMERIC(19, 2),
    vendedor_id      BIGINT REFERENCES usuarios (usu_id),
    comprador_id     BIGINT REFERENCES usuarios (usu_id)
);
