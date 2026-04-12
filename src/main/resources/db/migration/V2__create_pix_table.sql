CREATE TABLE pix (
    pix_id         BIGSERIAL PRIMARY KEY,
    pix_tipo       VARCHAR(20)  NOT NULL,
    pix_chave      VARCHAR(255) NOT NULL,
    pix_usuario_id BIGINT       NOT NULL REFERENCES usuarios (usu_id)
);
