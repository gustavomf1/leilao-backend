package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados permitidos para atualização de funcionário")
public record UsuarioFuncionarioUpdateDTO(
        @Schema(description = "Nome atualizado do funcionário", example = "Gustavo Martins Alterado")
        String nome,

        @Schema(description = "Novo tipo de perfil caso necessário", example = "FUNCIONARIO")
        TipoUsuario tipo
) {}