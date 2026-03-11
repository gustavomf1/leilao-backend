package backstage.project.erpleilao.dtos;

import java.time.LocalDate;

public record LeilaoDTO(
        Long id,
        String local,
        String uf,
        String cidade,
        String descricao,
        LocalDate data,
        Long condicoesId,
        Long taxasId
) {
}
