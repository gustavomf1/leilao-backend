package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.FazendaRequestDTO;
import backstage.project.erpleilao.dtos.FazendaResponseDTO;
import backstage.project.erpleilao.entity.*;
import backstage.project.erpleilao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FazendaService {

    @Autowired
    private FazendaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<FazendaResponseDTO> listar() {
        return repository.findAllAtivas().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FazendaResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .filter(f -> "N".equals(f.getInativo()))
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada ou inativa"));
    }

    @Transactional
    public FazendaResponseDTO salvar(FazendaRequestDTO dto) {
        Fazenda fazenda = new Fazenda();
        mapRequestToEntity(dto, fazenda);
        return convertToResponseDTO(repository.save(fazenda));
    }

    @Transactional
    public FazendaResponseDTO atualizar(Long id, FazendaRequestDTO dto) {
        Fazenda fazenda = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada"));
        mapRequestToEntity(dto, fazenda);
        return convertToResponseDTO(repository.save(fazenda));
    }

    @Transactional
    public void excluir(Long id) {
        Fazenda fazenda = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada para exclusão"));

        fazenda.setInativo("S");
        repository.save(fazenda);
    }

    private void mapRequestToEntity(FazendaRequestDTO dto, Fazenda entity) {
        entity.setNome(dto.nome());
        entity.setInscricao(dto.inscricao());
        entity.setCnpj(dto.cnpj());
        entity.setUf(dto.uf());
        entity.setCidade(dto.cidade());

        if (entity.getInativo() == null) {
            entity.setInativo("N");
        }

        UsuarioEntity titular = usuarioRepository.findById(dto.titularId())
                .orElseThrow(() -> new RuntimeException("Usuário titular não encontrado"));
        entity.setFaz_titular(titular);
    }

    private FazendaResponseDTO convertToResponseDTO(Fazenda fazenda) {
        return new FazendaResponseDTO(
                fazenda.getId(),
                fazenda.getNome(),
                fazenda.getInscricao(),
                fazenda.getCnpj(),
                fazenda.getUf(),
                fazenda.getCidade(),
                fazenda.getInativo(),
                fazenda.getDt_criacao(),
                fazenda.getFaz_titular().getUsu_nome()
        );
    }
}