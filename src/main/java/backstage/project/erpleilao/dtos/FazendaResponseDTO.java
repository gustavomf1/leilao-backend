package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Representação detalhada de uma fazenda")
public record FazendaResponseDTO(
        Long id,

        String nome,

        String inscricao,

        String cnpj,

        String uf,

        String cidade,

        String inativo,

        Date dtCriacao,

        @Schema(description = "Nome do proprietário da fazenda")
        String titularNome
) {}
