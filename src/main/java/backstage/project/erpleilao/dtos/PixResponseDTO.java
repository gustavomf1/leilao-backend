package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.TipoChavePix;

public record PixResponseDTO(
        Long pixId,
        TipoChavePix tipo,
        String chave,
        Long usuarioId,
        String usuarioNome
) {}
