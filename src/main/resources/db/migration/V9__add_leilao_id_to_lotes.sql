ALTER TABLE lotes ADD COLUMN leilao_id BIGINT REFERENCES leilao(lei_id);
