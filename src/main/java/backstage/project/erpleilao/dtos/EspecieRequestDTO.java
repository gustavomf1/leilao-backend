package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastro de espécie animal")
public record EspecieRequestDTO(
        @Schema(example = "BOVINOS")
        String nome
) {}
