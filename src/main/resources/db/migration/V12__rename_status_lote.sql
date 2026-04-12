-- Renomeia os status para refletir o fluxo correto
UPDATE lotes SET status = 'AGUARDANDO_ESCRITORIO' WHERE status = 'PENDENTE_ESCRITORIO';
UPDATE lotes SET status = 'AGUARDANDO_PRECO'      WHERE status = 'PENDENTE_PRECO';

-- Atualiza o default da coluna
ALTER TABLE lotes ALTER COLUMN status SET DEFAULT 'AGUARDANDO_ESCRITORIO';
