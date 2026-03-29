package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.AceiteIntegrado;
import backstage.project.erpleilao.entity.enums.TipoCondicao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro de condições de pagamento")
public record CondicaoRequestDTO(
        @Schema(example = "10 DIAS DIRETO")
        String descricao,

        @Schema(example = "1")
        Integer captacao,

        @Schema(example = "1")
        Integer parcelas,

        @Schema(example = "10")
        Integer qtdDias,

        @Schema(example = "0")
        BigDecimal percentualDesconto,

        @Schema(example = "N", description = "S para Sim, N para Não")
        String comEntrada,

        @Schema(example = "N", description = "S para Sim, N para Não")
        String mesmoDia,

        @Schema(example = "CORTE")
        TipoCondicao tipoCondicao,

        @Schema(example = "NORMAL")
        AceiteIntegrado aceiteIntegrado
) {
}
