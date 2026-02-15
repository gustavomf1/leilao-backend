package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados necessários para cadastrar um novo cliente")
public record UsuarioClienteDTO(
        @Schema(description = "Nome completo do cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Gustavo Martins")
        String nome,

        @Schema(description = "E-mail para login e notificações", requiredMode = Schema.RequiredMode.REQUIRED, example = "gustavo@email.com")
        String email,

        @Schema(description = "CPF único do cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "12345678901")
        String cpf,

        @Schema(description = "Telefone celular", example = "18991234567")
        String telefone,

        @Schema(description = "Cidade", example = "Presidente Prudente")
        String cidade,

        @Schema(description = "Estado", example = "SP")
        String uf,

        @Schema(description = "Documento RG", example = "12.345.678-9")
        String rg
) {}
