-- Substitui a flag em_andamento por um status textual
ALTER TABLE leilao ADD COLUMN lei_status VARCHAR(20) NOT NULL DEFAULT 'ABERTO';

-- Migra dados existentes
UPDATE leilao SET lei_status = 'EM_ANDAMENTO' WHERE lei_em_andamento = 'S';

-- Remove a coluna antiga
ALTER TABLE leilao DROP COLUMN lei_em_andamento;
