-- Cria tabela de espécies
CREATE TABLE IF NOT EXISTS especies (
    esp_id     BIGSERIAL PRIMARY KEY,
    esp_nome   VARCHAR(50) NOT NULL UNIQUE,
    esp_inativo CHAR(1)    NOT NULL DEFAULT 'N'
);

-- Popula espécies a partir dos valores distintos já existentes em taxas_comissao
INSERT INTO especies (esp_nome)
SELECT DISTINCT UPPER(tax_especie)
FROM taxas_comissao
WHERE tax_especie IS NOT NULL AND TRIM(tax_especie) <> ''
ON CONFLICT (esp_nome) DO NOTHING;

-- Adiciona coluna de FK na tabela de taxas
ALTER TABLE taxas_comissao
    ADD COLUMN IF NOT EXISTS tax_especie_id BIGINT;

-- Preenche o FK com base no nome da espécie
UPDATE taxas_comissao tc
SET tax_especie_id = e.esp_id
FROM especies e
WHERE UPPER(tc.tax_especie) = e.esp_nome;

-- Garante que não ficou nenhum nulo (fallback: cria espécie "OUTROS" se necessário)
INSERT INTO especies (esp_nome) VALUES ('OUTROS') ON CONFLICT DO NOTHING;

UPDATE taxas_comissao
SET tax_especie_id = (SELECT esp_id FROM especies WHERE esp_nome = 'OUTROS')
WHERE tax_especie_id IS NULL;

-- Aplica NOT NULL e FK após migração dos dados
ALTER TABLE taxas_comissao
    ALTER COLUMN tax_especie_id SET NOT NULL,
    ADD CONSTRAINT fk_taxa_especie FOREIGN KEY (tax_especie_id) REFERENCES especies(esp_id);

-- Remove coluna antiga
ALTER TABLE taxas_comissao
    DROP COLUMN IF EXISTS tax_especie;
