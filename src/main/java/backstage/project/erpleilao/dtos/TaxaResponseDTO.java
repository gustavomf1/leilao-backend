package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.TaxaPor;
import backstage.project.erpleilao.entity.enums.TipoLeilao;

import java.math.BigDecimal;

public record TaxaResponseDTO(
        Long id,
        BigDecimal comissaoVendedor,
        BigDecimal comissaoComprador,
        Long especieId,
        String especieNome,
        TipoLeilao tipoLeilao,
        TaxaPor taxaPor,
        String inativo
) {}
