package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de uma permissão (ação + ambiente)")
public record PermissaoDTO(
        @Schema(description = "Ação permitida", example = "CRIAR")
        String acao,

        @Schema(description = "Ambiente/módulo onde a ação se aplica", example = "LEILOES")
        String ambiente
) {}
