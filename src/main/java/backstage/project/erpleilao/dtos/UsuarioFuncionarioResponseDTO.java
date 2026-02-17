package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.UsuarioEntity;
import backstage.project.erpleilao.entity.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

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
        LocalDateTime dataCriacao
) {
    public UsuarioFuncionarioResponseDTO(UsuarioEntity usuario) {
        this(
                usuario.getUsu_id(),
                usuario.getUsu_nome(),
                usuario.getUsu_email(),
                usuario.getUsu_cpf(),
                usuario.getUsu_tipo(),
                usuario.getUsu_inativo(),
                usuario.getUsu_dt_criacao()
        );
    }
}