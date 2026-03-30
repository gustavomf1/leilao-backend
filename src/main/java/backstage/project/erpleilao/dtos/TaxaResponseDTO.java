package backstage.project.erpleilao.dtos;

import java.math.BigDecimal;

public record TaxaResponseDTO(
        Long id,
        BigDecimal comissaoVendedor,
        BigDecimal comissaoComprador,
        String especie,
        String tipoLeilao,
        String inativo
) {}
