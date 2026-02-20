package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação ou atualização de uma fazenda")
public record FazendaRequestDTO(
        @Schema(example = "Fazenda Santa Maria")
        String nome,

        @Schema(example = "123456789")
        String inscricao,

        @Schema(example = "12.345.678/0001-99")
        String cnpj,

        @Schema(example = "SP")
        String uf,

        @Schema(example = "Regente Feijó")
        String cidade,

        @Schema(description = "ID do usuário titular da fazenda", example = "1")
        Long titularId
) {}
