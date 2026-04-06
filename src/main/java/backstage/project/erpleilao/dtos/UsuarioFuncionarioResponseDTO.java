package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.RoleEntity;
import backstage.project.erpleilao.entity.UsuarioEntity;
import backstage.project.erpleilao.entity.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Dados de retorno do funcionário")
public record UsuarioFuncionarioResponseDTO(
        @Schema(description = "ID único no banco de dados", example = "1")
        Long id,

        @Schema(description = "Nome do funcionário", example = "Gustavo Martins")
        String nome,

        @Schema(description = "E-mail cadastrado", example = "admin@agrolance.com.br")
        String email,

        @Schema(description = "CPF cadastrado", example = "12345678901")
        String cpf,

        @Schema(description = "Tipo de perfil (Hierarquia)", example = "FUNCIONARIO")
        TipoUsuario tipo,

        @Schema(description = "Status de atividade (S/N)", example = "N")
        String inativo,

        @Schema(description = "Data e hora de criação do registro")
        LocalDateTime dataCriacao,

        @Schema(description = "Se o funcionário é administrador")
        Boolean isAdmin,

        @Schema(description = "Roles atribuídas ao funcionário")
        List<RoleResponseDTO> roles,

        @Schema(description = "Se o funcionário é do time de manejo de campo")
        Boolean isManejo
) {
    public UsuarioFuncionarioResponseDTO(UsuarioEntity usuario) {
        this(
                usuario.getUsu_id(),
                usuario.getUsu_nome(),
                usuario.getUsu_email(),
                usuario.getUsu_cpf(),
                usuario.getUsu_tipo(),
                usuario.getUsu_inativo(),
                usuario.getUsu_dt_criacao(),
                usuario.getUsu_is_admin(),
                usuario.getUsu_roles() != null
                        ? usuario.getUsu_roles().stream().map(RoleResponseDTO::new).toList()
                        : List.of(),
                Boolean.TRUE.equals(usuario.getUsu_is_manejo())
        );
    }
}
