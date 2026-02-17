package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.LoteDisplayDTO;
import backstage.project.erpleilao.dtos.LoteRequestDTO;
import backstage.project.erpleilao.entity.LoteEntity;
import backstage.project.erpleilao.repository.LoteRepository;
import backstage.project.erpleilao.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate; // MUDANÇA AQUI
import org.springframework.data.redis.listener.ChannelTopic; // MUDANÇA AQUI
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoteService {

    @Autowired
    private LoteRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    @Transactional
    public LoteDisplayDTO cadastrar(LoteRequestDTO dados) {
        var vendedor = usuarioRepository.findById(dados.vendedorId())
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado"));

        var comprador = dados.compradorId() != null ?
                usuarioRepository.findById(dados.compradorId()).orElse(null) : null;

        var lote = new LoteEntity();
        lote.setCodigo(dados.codigo());
        lote.setQntdAnimais(dados.qntdAnimais());
        lote.setSexo(dados.sexo());
        lote.setIdadeEmMeses(dados.idadeEmMeses());
        lote.setPeso(dados.peso());
        lote.setRaca(dados.raca());
        lote.setEspecie(dados.especie());
        lote.setCategoriaAnimal(dados.categoriaAnimal());
        lote.setObs(dados.obs());
        lote.setPrecoCompra(dados.precoCompra());

        lote.setVendedor(vendedor);
        lote.setComprador(comprador);

        var loteSalvo = repository.save(lote);

        var displayDto = new LoteDisplayDTO(loteSalvo);

        redisTemplate.convertAndSend(topic.getTopic(), displayDto);

        return displayDto;
    }

    public List<LoteDisplayDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(LoteDisplayDTO::new)
                .toList();
    }
}