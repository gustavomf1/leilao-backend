package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.LoteEntity;

import java.math.BigDecimal;

public record LoteDisplayDTO(
        Long id,
        String codigo,
        Integer qntdAnimais,
        String sexo,
        Integer idadeEmMeses,
        Double peso,
        String raca,
        String especie,
        String categoriaAnimal,
        String obs,
        Long leilaoId,
        Long vendedorId,
        Long compradorId,
        Double precoCompra,
        String vendedorNome
) {
    public LoteDisplayDTO(LoteEntity lote) {
        this(
                lote.getId(),
                lote.getCodigo(),
                lote.getQntdAnimais(),
                lote.getSexo(),
                lote.getIdadeEmMeses(),
                lote.getPeso(),
                lote.getRaca(),
                lote.getEspecie(),
                lote.getCategoriaAnimal(),
                lote.getObs(),
                null, // leilaoId - adicione o relacionamento se tiver
                lote.getVendedor() != null ? lote.getVendedor().getUsu_id() : null,
                lote.getComprador() != null ? lote.getComprador().getUsu_id() : null,
                lote.getPrecoCompra() != null ? lote.getPrecoCompra().doubleValue() : null,
                lote.getVendedor() != null ? lote.getVendedor().getUsu_nome() : "Vendedor não informado"
        );
    }
}
