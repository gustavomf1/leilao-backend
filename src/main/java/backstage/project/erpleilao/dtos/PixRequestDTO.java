package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.TipoChavePix;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PixRequestDTO(
        @NotNull Long usuarioId,
        @NotNull TipoChavePix tipo,
        @NotBlank String chave
) {}
