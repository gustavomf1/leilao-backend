package backstage.project.erpleilao.dtos;

import java.math.BigDecimal;

public record TaxaResponseDTO(
        Long id,
        BigDecimal porcentagem,
        String tipoCliente,
        String inativo
) {}
