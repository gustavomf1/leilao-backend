package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro de taxas de comissão")
public record TaxaRequestDTO(
        @Schema(example = "5.00")
        BigDecimal porcentagem,

        @Schema(example = "COMPRADOR")
        String tipoCliente
) {}
