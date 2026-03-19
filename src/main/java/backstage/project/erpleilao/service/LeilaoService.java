package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.LeilaoDTO;
import backstage.project.erpleilao.entity.CondicaoEntity;
import backstage.project.erpleilao.entity.LeilaoEntity;
import backstage.project.erpleilao.entity.TaxaComissaoEntity;
import backstage.project.erpleilao.repository.CondicaoRepository;
import backstage.project.erpleilao.repository.LeilaoRepository;
import backstage.project.erpleilao.repository.TaxaComissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository leilaoRepository;

    @Autowired
    private CondicaoRepository condicoesRepository;

    @Autowired
    private TaxaComissaoRepository taxasRepository;

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

    public void deletar(Long id) {
        leilaoRepository.deleteById(id);
    }

    private LeilaoEntity salvarDados(LeilaoEntity leilao, LeilaoDTO dto) {
        leilao.setLocal(dto.local());
        leilao.setUf(dto.uf());
        leilao.setCidade(dto.cidade());
        leilao.setDescricao(dto.descricao());
        leilao.setData(dto.data());

        CondicaoEntity condicoes = condicoesRepository.findById(dto.condicoesId())
                .orElseThrow(() -> new RuntimeException("Condição não encontrada"));
        leilao.setCondicao(condicoes);

        TaxaComissaoEntity taxas = taxasRepository.findById(dto.taxasId())
                .orElseThrow(() -> new RuntimeException("Taxa não encontrada"));
        leilao.setTaxa(taxas);

        return leilaoRepository.save(leilao);
    }
}