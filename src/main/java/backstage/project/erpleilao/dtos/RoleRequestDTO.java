package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação/atualização de uma role")
public record RoleRequestDTO(
        @Schema(description = "Nome da role", requiredMode = Schema.RequiredMode.REQUIRED, example = "GERENCIAR_LEILOES")
        String nome,

        @Schema(description = "Descrição da role", example = "Permite gerenciar leilões")
        String descricao
) {}
