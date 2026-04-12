package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.TaxaPor;
import backstage.project.erpleilao.entity.enums.TipoLeilao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro de taxas de comissão")
public record TaxaRequestDTO(
        @Schema(example = "5.00")
        BigDecimal comissaoVendedor,

        @Schema(example = "5.00")
        BigDecimal comissaoComprador,

        @Schema(example = "1")
        Long especieId,

        @Schema(example = "CORTE")
        TipoLeilao tipoLeilao,

        @Schema(example = "ANIMAL")
        TaxaPor taxaPor
) {}
