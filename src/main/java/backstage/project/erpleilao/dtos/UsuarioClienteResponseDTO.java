package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.UsuarioEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de retorno do cliente")
public record UsuarioClienteResponseDTO(
        @Schema(description = "ID único gerado pelo banco", example = "1")
        Long id,

        @Schema(description = "Nome completo do cliente", example = "Gustavo Martins")
        String nome,

        @Schema(description = "E-mail cadastrado", example = "gustavo@email.com")
        String email,

        @Schema(description = "CPF (apenas números)", example = "12345678901")
        String cpf,

        @Schema(description = "Telefone de contato", example = "18991234567")
        String telefone,

        @Schema(description = "Cidade", example = "Regente Feijó")
        String cidade,

        @Schema(description = "Estado (UF)", example = "SP")
        String uf
) {
    public UsuarioClienteResponseDTO(UsuarioEntity usuario) {
        this(usuario.getUsu_id(), usuario.getUsu_nome(), usuario.getUsu_email(),
                usuario.getUsu_cpf(), usuario.getUsu_telefone(), usuario.getUsu_cidade(),
                usuario.getUsu_uf());
    }
}
