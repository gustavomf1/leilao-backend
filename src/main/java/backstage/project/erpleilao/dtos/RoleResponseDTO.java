package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.RoleEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dados de retorno de uma role")
public record RoleResponseDTO(
        @Schema(description = "ID da role", example = "1")
        Long id,

        @Schema(description = "Nome da role", example = "GERENCIAR_LEILOES")
        String nome,

        @Schema(description = "Descrição da role", example = "Permite gerenciar leilões")
        String descricao,

        @Schema(description = "Lista de permissões da role")
        List<PermissaoDTO> permissoes
) {
    public RoleResponseDTO(RoleEntity role) {
        this(
                role.getRole_id(),
                role.getRole_nome(),
                role.getRole_descricao(),
                role.getPermissoes() != null
                        ? role.getPermissoes().stream()
                        .map(p -> new PermissaoDTO(p.getAcao().name(), p.getAmbiente().name()))
                        .toList()
                        : List.of()
        );
    }
}
