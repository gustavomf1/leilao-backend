ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS usu_is_admin BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE IF NOT EXISTS roles (
    role_id       BIGSERIAL PRIMARY KEY,
    role_nome     VARCHAR(255) NOT NULL UNIQUE,
    role_descricao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS usuario_roles (
    usuario_id BIGINT NOT NULL REFERENCES usuarios (usu_id),
    role_id    BIGINT NOT NULL REFERENCES roles (role_id),
    PRIMARY KEY (usuario_id, role_id)
);
