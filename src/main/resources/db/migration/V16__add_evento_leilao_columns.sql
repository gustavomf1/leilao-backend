-- Flag para indicar que o lote nao foi vendido no leilao
ALTER TABLE lotes ADD COLUMN nao_vendido_no_leilao VARCHAR(1) NOT NULL DEFAULT 'N';

-- Flag para indicar que o leilao esta em andamento (evento ativo)
ALTER TABLE leilao ADD COLUMN lei_em_andamento VARCHAR(1) NOT NULL DEFAULT 'N';
