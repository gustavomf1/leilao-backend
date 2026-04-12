package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.LeilaoDTO;
import backstage.project.erpleilao.dtos.LeilaoResumoDTO;
import backstage.project.erpleilao.dtos.LoteDisplayDTO;
import backstage.project.erpleilao.entity.CondicaoEntity;
import backstage.project.erpleilao.entity.LeilaoEntity;
import backstage.project.erpleilao.entity.LoteEntity;
import backstage.project.erpleilao.entity.TaxaComissaoEntity;
import backstage.project.erpleilao.entity.enums.StatusLeilao;
import backstage.project.erpleilao.entity.enums.StatusLote;
import backstage.project.erpleilao.repository.CondicaoRepository;
import backstage.project.erpleilao.repository.LeilaoRepository;
import backstage.project.erpleilao.repository.LoteRepository;
import backstage.project.erpleilao.repository.TaxaComissaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository leilaoRepository;

    @Autowired
    private CondicaoRepository condicoesRepository;

    @Autowired
    private TaxaComissaoRepository taxasRepository;

    @Autowired
    private LoteRepository loteRepository;

    public List<LeilaoEntity> listarTodos() {
        return leilaoRepository.findAll();
    }

    public LeilaoEntity buscarPorId(Long id) {
        return leilaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leilão não encontrado"));
    }

    public LeilaoEntity criar(LeilaoDTO dto) {
        LeilaoEntity leilao = new LeilaoEntity();
        return salvarDados(leilao, dto);
    }

    public LeilaoEntity atualizar(Long id, LeilaoDTO dto) {
        LeilaoEntity leilao = buscarPorId(id);
        return salvarDados(leilao, dto);
    }

    public LeilaoResumoDTO buscarResumo(Long id) {
        LeilaoEntity leilao = buscarPorId(id);
        List<LoteEntity> lotes = loteRepository.findByLeilaoId(id);

        int totalLotes = lotes.size();
        int lotesVendidos = (int) lotes.stream().filter(l -> l.getComprador() != null).count();
        int totalAnimais = lotes.stream().mapToInt(l -> l.getQntdAnimais() != null ? l.getQntdAnimais() : 0).sum();
        int animaisVendidos = lotes.stream().filter(l -> l.getComprador() != null)
                .mapToInt(l -> l.getQntdAnimais() != null ? l.getQntdAnimais() : 0).sum();

        BigDecimal movimentacaoBruta = lotes.stream()
                .filter(l -> l.getComprador() != null && l.getPrecoCompra() != null)
                .map(LoteEntity::getPrecoCompra)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal ticketMedio = lotesVendidos > 0
                ? movimentacaoBruta.divide(BigDecimal.valueOf(lotesVendidos), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        TaxaComissaoEntity taxa = leilao.getTaxa();
        CondicaoEntity condicao = leilao.getCondicao();

        BigDecimal receitaComissao = BigDecimal.ZERO;
        if (taxa != null) {
            BigDecimal percTotal = (taxa.getComissaoVendedor() != null ? taxa.getComissaoVendedor() : BigDecimal.ZERO)
                    .add(taxa.getComissaoComprador() != null ? taxa.getComissaoComprador() : BigDecimal.ZERO);
            receitaComissao = movimentacaoBruta.multiply(percTotal)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        }

        return new LeilaoResumoDTO(
                leilao.getId(), leilao.getDescricao(), leilao.getLocal(),
                leilao.getCidade(), leilao.getUf(), leilao.getData(),
                condicao != null ? condicao.getDescricao() : null,
                taxa != null ? taxa.getComissaoVendedor() : null,
                taxa != null ? taxa.getComissaoComprador() : null,
                taxa != null && taxa.getEspecie() != null ? taxa.getEspecie().getNome() : null,
                taxa != null && taxa.getTipoLeilao() != null ? taxa.getTipoLeilao().name() : null,
                taxa != null && taxa.getTaxaPor() != null ? taxa.getTaxaPor().name() : null,
                totalLotes, lotesVendidos, totalLotes - lotesVendidos,
                totalAnimais, animaisVendidos, movimentacaoBruta, receitaComissao, ticketMedio,
                lotes.stream()
                        .filter(l -> l.getStatus() == StatusLote.AGUARDANDO_LANCE || l.getStatus() == StatusLote.FINALIZADO)
                        .map(LoteDisplayDTO::new).toList()
        );
    }

    @Transactional
    public LeilaoEntity iniciarLeilao(Long id) {
        LeilaoEntity leilao = buscarPorId(id);

        if (leilao.getStatus() != StatusLeilao.ABERTO) {
            throw new IllegalStateException("Leilão só pode ser iniciado quando está com status ABERTO");
        }

        List<LoteEntity> lotes = loteRepository.findByLeilaoId(id);
        if (lotes.isEmpty()) {
            throw new IllegalStateException("Leilão não possui lotes vinculados");
        }

        leilao.setStatus(StatusLeilao.EM_ANDAMENTO);
        return leilaoRepository.save(leilao);
    }

    @Transactional
    public LeilaoEntity encerrarLeilao(Long id) {
        LeilaoEntity leilao = buscarPorId(id);

        if (leilao.getStatus() != StatusLeilao.EM_ANDAMENTO) {
            throw new IllegalStateException("Leilão só pode ser encerrado quando está EM_ANDAMENTO");
        }

        List<LoteEntity> lotes = loteRepository.findByLeilaoId(id);

        // Lotes que permaneceram sem lance recebem a flag nao_vendido_no_leilao = "S"
        for (LoteEntity lote : lotes) {
            if (lote.getStatus() == StatusLote.AGUARDANDO_LANCE && lote.getPrecoCompra() == null) {
                lote.setNaoVendidoNoLeilao("S");
            }
        }

        leilao.setStatus(StatusLeilao.FINALIZADO);
        return leilaoRepository.save(leilao);
    }

    public void deletar(Long id) {
        leilaoRepository.deleteById(id);
    }

    private LeilaoEntity salvarDados(LeilaoEntity leilao, LeilaoDTO dto) {
        leilao.setLocal(dto.local());
        leilao.setUf(dto.uf());
        leilao.setCidade(dto.cidade());
        leilao.setDescricao(dto.descricao());
        leilao.setData(dto.data());

        if (dto.condicoesId() != null) {
            CondicaoEntity condicoes = condicoesRepository.findById(dto.condicoesId())
                    .orElseThrow(() -> new RuntimeException("Condição não encontrada"));
            leilao.setCondicao(condicoes);
        } else {
            leilao.setCondicao(null);
        }

        if (dto.taxasId() != null) {
            TaxaComissaoEntity taxas = taxasRepository.findById(dto.taxasId())
                    .orElseThrow(() -> new RuntimeException("Taxa não encontrada"));
            leilao.setTaxa(taxas);
        } else {
            leilao.setTaxa(null);
        }

        return leilaoRepository.save(leilao);
    }
}