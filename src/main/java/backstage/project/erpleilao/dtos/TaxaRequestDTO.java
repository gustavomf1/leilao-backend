package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro de taxas de comissão")
public record TaxaRequestDTO(
        @Schema(example = "5.00")
        BigDecimal comissaoVendedor,

        @Schema(example = "5.00")
        BigDecimal comissaoComprador,

        @Schema(example = "BOVINOS")
        String especie,

        @Schema(example = "Corte")
        String tipoLeilao
) {}
