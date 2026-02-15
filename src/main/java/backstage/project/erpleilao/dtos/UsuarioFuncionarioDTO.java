package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastro de um novo funcionário administrativo")
public record UsuarioFuncionarioDTO(
        @Schema(description = "Nome completo do funcionário", example = "Gustavo Martins")
        String nome,

        @Schema(description = "E-mail corporativo", example = "admin@agrolance.com.br")
        String email,

        @Schema(description = "CPF (apenas números)", example = "12345678901")
        String cpf,

        @Schema(description = "Senha de acesso ao sistema", example = "senha123", writeOnly = true)
        String senha
) { }