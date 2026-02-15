package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto que contém o token de acesso gerado")
public record DadosTokenJWT(
        @Schema(description = "Token JWT no formato Bearer", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {
}
