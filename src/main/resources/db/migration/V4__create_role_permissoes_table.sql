CREATE TABLE IF NOT EXISTS role_permissoes (
    id       BIGSERIAL PRIMARY KEY,
    role_id  BIGINT NOT NULL REFERENCES roles (role_id),
    acao     VARCHAR(50) NOT NULL,
    ambiente VARCHAR(50) NOT NULL,
    CONSTRAINT uq_role_acao_ambiente UNIQUE (role_id, acao, ambiente)
);