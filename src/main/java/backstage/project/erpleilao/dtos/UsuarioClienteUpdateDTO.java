package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para atualização de um cliente existente")
public record UsuarioClienteUpdateDTO(
        @Schema(description = "Nome completo do cliente", example = "Gustavo Martins")
        String nome,
        @Schema(description = "Telefone de contato com DDD", example = "18991234567")
        String telefone,
        @Schema(description = "Cidade de residência", example = "Regente Feijó")
        String cidade,
        @Schema(description = "Unidade Federativa", example = "SP", maxLength = 2)
        String uf,
        @Schema(description = "Registro Geral (RG)", example = "12.345.678-9")
        String rg
) {}
