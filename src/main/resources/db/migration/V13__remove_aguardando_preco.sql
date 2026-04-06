-- AGUARDANDO_PRECO removido do fluxo — lotes nesse status vão direto para AGUARDANDO_LANCE
UPDATE lotes SET status = 'AGUARDANDO_LANCE' WHERE status = 'AGUARDANDO_PRECO';
