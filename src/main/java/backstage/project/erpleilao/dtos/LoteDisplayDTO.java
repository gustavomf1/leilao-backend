package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.LoteEntity;

import java.math.BigDecimal;

public record LoteDisplayDTO(
        Long id,
        String codigo,
        Integer qntdAnimais,
        String raca,
        BigDecimal precoCompra,
        String vendedorNome
) {
    public LoteDisplayDTO(LoteEntity lote) {
        this(
                lote.getId(),
                lote.getCodigo(),
                lote.getQntdAnimais(),
                lote.getRaca(),
                lote.getPrecoCompra(),
                lote.getVendedor() != null ? lote.getVendedor().getUsu_nome() : "Vendedor não informado"
        );
    }
}
