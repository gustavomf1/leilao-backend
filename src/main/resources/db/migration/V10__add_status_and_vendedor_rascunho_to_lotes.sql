-- Adiciona nome informal do vendedor (preenchido pelo manejo)
ALTER TABLE lotes ADD COLUMN IF NOT EXISTS vendedor_nome_rascunho VARCHAR(255);

-- Adiciona coluna de status do fluxo do lote
ALTER TABLE lotes ADD COLUMN IF NOT EXISTS status VARCHAR(50) NOT NULL DEFAULT 'RASCUNHO';

-- Lotes já existentes recebem AGUARDANDO_LANCE (já passaram pelo fluxo completo)
UPDATE lotes SET status = 'AGUARDANDO_LANCE' WHERE status = 'RASCUNHO' AND preco_compra IS NOT NULL AND vendedor_id IS NOT NULL;
