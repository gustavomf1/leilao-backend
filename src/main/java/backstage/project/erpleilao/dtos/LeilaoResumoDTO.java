package backstage.project.erpleilao.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record LeilaoResumoDTO(
        Long id,
        String descricao,
        String local,
        String cidade,
        String uf,
        LocalDate data,
        String condicaoDescricao,
        BigDecimal comissaoVendedor,
        BigDecimal comissaoComprador,
        String especieNome,
        String tipoLeilao,
        String taxaPor,
        int totalLotes,
        int lotesVendidos,
        int lotesRestantes,
        int totalAnimais,
        int animaisVendidos,
        BigDecimal movimentacaoBruta,
        BigDecimal receitaComissao,
        BigDecimal ticketMedio,
        List<LoteDisplayDTO> lotes
) {}
