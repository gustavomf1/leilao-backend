CREATE TABLE recuperacao_senha (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES usuarios(usu_id),
    token VARCHAR(255) NOT NULL,
    data_expiracao TIMESTAMP NOT NULL,
    usado VARCHAR(1) NOT NULL DEFAULT 'N'
);