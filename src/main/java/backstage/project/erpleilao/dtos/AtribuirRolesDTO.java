package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dados para atribuir roles a um funcionário")
public record AtribuirRolesDTO(
        @Schema(description = "Lista de IDs das roles a serem atribuídas", example = "[1, 2, 3]")
        List<Long> roleIds,

        @Schema(description = "Define se o funcionário é administrador", example = "false")
        Boolean isAdmin
) {}
