package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.LoteEntity;
import backstage.project.erpleilao.entity.enums.StatusLote;

import java.math.BigDecimal;

public record LoteDisplayDTO(
        Long id,
        String codigo,
        Integer qntdAnimais,
        String sexo,
        Integer idadeEmMeses,
        Double peso,
        String raca,
        Long especieId,
        String especieNome,
        String categoriaAnimal,
        String obs,
        Long leilaoId,
        Long vendedorId,
        Long compradorId,
        Double precoCompra,
        String vendedorNome,
        String vendedorNomeRascunho,
        StatusLote status,
        String naoVendidoNoLeilao
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
                lote.getEspecie() != null ? lote.getEspecie().getId()   : null,
                lote.getEspecie() != null ? lote.getEspecie().getNome() : null,
                lote.getCategoriaAnimal(),
                lote.getObs(),
                lote.getLeilao()    != null ? lote.getLeilao().getId()           : null,
                lote.getVendedor()  != null ? lote.getVendedor().getUsu_id()     : null,
                lote.getComprador() != null ? lote.getComprador().getUsu_id()    : null,
                lote.getPrecoCompra() != null ? lote.getPrecoCompra().doubleValue() : null,
                lote.getVendedor()  != null ? lote.getVendedor().getUsu_nome()   : null,
                lote.getVendedorNomeRascunho(),
                lote.getStatus(),
                lote.getNaoVendidoNoLeilao()
        );
    }
}
