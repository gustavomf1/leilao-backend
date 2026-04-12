-- Adiciona FK especie_id na tabela lotes
ALTER TABLE lotes ADD COLUMN especie_id BIGINT;

-- Tenta migrar dados existentes pelo nome (best-effort)
UPDATE lotes l
SET especie_id = (
    SELECT e.esp_id FROM especies e WHERE e.esp_nome = l.especie LIMIT 1
)
WHERE l.especie IS NOT NULL;

-- Adiciona constraint FK (nullable — lotes antigos sem match ficam NULL)
ALTER TABLE lotes
    ADD CONSTRAINT fk_lotes_especie
    FOREIGN KEY (especie_id) REFERENCES especies(esp_id);

-- Remove a coluna legada
ALTER TABLE lotes DROP COLUMN especie;
