package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.AceiteIntegrado;
import backstage.project.erpleilao.entity.enums.TipoCondicao;

import java.math.BigDecimal;

public record CondicaoResponseDTO(
        Long id,
        String descricao,
        Integer captacao,
        Integer parcelas,
        Integer qtdDias,
        BigDecimal percentualDesconto,
        String comEntrada,
        String mesmoDia,
        TipoCondicao tipoCondicao,
        AceiteIntegrado aceiteIntegrado,
        String inativo
) {}
