-- RASCUNHO foi removido do fluxo — lotes nesse status viram PENDENTE_PRECO
UPDATE lotes SET status = 'PENDENTE_PRECO' WHERE status = 'RASCUNHO';
