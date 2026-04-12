package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.LoteDisplayDTO;
import backstage.project.erpleilao.dtos.LoteRequestDTO;
import backstage.project.erpleilao.entity.LoteEntity;
import backstage.project.erpleilao.entity.enums.StatusLote;
import backstage.project.erpleilao.repository.EspecieRepository;
import backstage.project.erpleilao.repository.LeilaoRepository;
import backstage.project.erpleilao.repository.LoteRepository;
import backstage.project.erpleilao.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoteService {

    @Autowired
    private LoteRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private LeilaoRepository leilaoRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    @Transactional
    public LoteDisplayDTO cadastrar(LoteRequestDTO dados) {
        var lote = new LoteEntity();
        lote.setCodigo(dados.codigo());
        lote.setQntdAnimais(dados.qntdAnimais());
        lote.setSexo(dados.sexo());
        lote.setIdadeEmMeses(dados.idadeEmMeses());
        lote.setPeso(dados.peso());
        lote.setRaca(dados.raca());
        lote.setCategoriaAnimal(dados.categoriaAnimal());
        lote.setObs(dados.obs());
        lote.setVendedorNomeRascunho(dados.vendedorNomeRascunho());

        if (dados.especieId() != null) {
            var especie = especieRepository.findById(dados.especieId())
                    .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada"));
            lote.setEspecie(especie);
        }

        // Preço é opcional — manejo não preenche
        if (dados.precoCompra() != null) {
            lote.setPrecoCompra(dados.precoCompra());
        }

        // Vendedor por ID é opcional — escritório vincula depois
        if (dados.vendedorId() != null) {
            var vendedor = usuarioRepository.findById(dados.vendedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado"));
            lote.setVendedor(vendedor);
        }

        if (dados.compradorId() != null) {
            var comprador = usuarioRepository.findById(dados.compradorId()).orElse(null);
            lote.setComprador(comprador);
        }

        if (dados.leilaoId() != null) {
            var leilao = leilaoRepository.findById(dados.leilaoId())
                    .orElseThrow(() -> new EntityNotFoundException("Leilão não encontrado"));
            lote.setLeilao(leilao);
        }

        // Manejo sempre cria em AGUARDANDO_ESCRITORIO — preço é preenchido após o lance
        lote.setStatus(StatusLote.AGUARDANDO_ESCRITORIO);
        lote.setNaoVendidoNoLeilao("N");

        var loteSalvo = repository.save(lote);
        var dto = new LoteDisplayDTO(loteSalvo);
        redisTemplate.convertAndSend(topic.getTopic(), dto);
        return dto;
    }

    public List<LoteDisplayDTO> listarPorLeilaoPublico(Long leilaoId) {
        return repository
                .findByLeilaoIdAndStatusIn(leilaoId, List.of(StatusLote.AGUARDANDO_LANCE, StatusLote.FINALIZADO))
                .stream()
                .map(LoteDisplayDTO::new)
                .toList();
    }

    public List<LoteDisplayDTO> listarTodos(Boolean naoVendido) {
        List<LoteEntity> lotes = Boolean.TRUE.equals(naoVendido)
                ? repository.findByNaoVendidoNoLeilao("S")
                : repository.findAll();
        return lotes.stream().map(LoteDisplayDTO::new).toList();
    }

    public LoteDisplayDTO buscarPorId(Long id) {
        var lote = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote não encontrado"));
        return new LoteDisplayDTO(lote);
    }

    @Transactional
    public LoteDisplayDTO atualizar(Long id, LoteRequestDTO dados) {
        var lote = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote não encontrado"));

        lote.setCodigo(dados.codigo());
        lote.setQntdAnimais(dados.qntdAnimais());
        lote.setSexo(dados.sexo());
        lote.setIdadeEmMeses(dados.idadeEmMeses());
        lote.setPeso(dados.peso());
        lote.setRaca(dados.raca());
        lote.setCategoriaAnimal(dados.categoriaAnimal());
        lote.setObs(dados.obs());
        lote.setVendedorNomeRascunho(dados.vendedorNomeRascunho());

        if (dados.especieId() != null) {
            var especie = especieRepository.findById(dados.especieId())
                    .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada"));
            lote.setEspecie(especie);
        } else {
            lote.setEspecie(null);
        }

        if (dados.precoCompra() != null) {
            lote.setPrecoCompra(dados.precoCompra());
        }

        if (dados.vendedorId() != null) {
            var vendedor = usuarioRepository.findById(dados.vendedorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado"));
            lote.setVendedor(vendedor);
        }

        if (dados.compradorId() != null) {
            lote.setComprador(usuarioRepository.findById(dados.compradorId()).orElse(null));
        }

        if (dados.leilaoId() != null) {
            var leilao = leilaoRepository.findById(dados.leilaoId())
                    .orElseThrow(() -> new EntityNotFoundException("Leilão não encontrado"));
            lote.setLeilao(leilao);
        }

        var dto = new LoteDisplayDTO(lote);
        redisTemplate.convertAndSend(topic.getTopic(), dto);
        return dto;
    }

    @Transactional
    public LoteDisplayDTO registrarPreco(Long id, java.math.BigDecimal preco) {
        var lote = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote não encontrado"));

        if (lote.getStatus() != StatusLote.AGUARDANDO_LANCE) {
            throw new IllegalStateException("Valor do lance só pode ser registrado quando o lote está em AGUARDANDO_LANCE");
        }

        lote.setPrecoCompra(preco);
        lote.setStatus(StatusLote.FINALIZADO);

        var dto = new LoteDisplayDTO(lote);
        redisTemplate.convertAndSend(topic.getTopic(), dto);
        return dto;
    }

    @Transactional
    public LoteDisplayDTO avancarStatus(Long id) {
        var lote = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote não encontrado"));

        StatusLote novoStatus = switch (lote.getStatus()) {
            case AGUARDANDO_ESCRITORIO -> StatusLote.AGUARDANDO_LANCE;
            case AGUARDANDO_LANCE      -> throw new IllegalStateException("Use o endpoint de preço para finalizar o lote (PATCH /{id}/preco)");
            case FINALIZADO            -> throw new IllegalStateException("Lote já está finalizado");
        };

        lote.setStatus(novoStatus);
        var dto = new LoteDisplayDTO(lote);
        redisTemplate.convertAndSend(topic.getTopic(), dto);
        return dto;
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Lote não encontrado");
        }
        repository.deleteById(id);
    }

}
