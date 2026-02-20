package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastro de condições de leilão")
public record CondicaoRequestDTO(
        @Schema(example = "PAGAMENTO")
        String tipo,

        @Schema(example = "30 parcelas (2+2+2+24)")
        String descricao
) {
}
