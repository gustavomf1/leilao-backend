package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.EspecieRequestDTO;
import backstage.project.erpleilao.dtos.EspecieResponseDTO;
import backstage.project.erpleilao.entity.EspecieEntity;
import backstage.project.erpleilao.repository.EspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository repository;

    @Transactional(readOnly = true)
    public List<EspecieResponseDTO> listar() {
        return repository.findAllAtivas().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EspecieResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EspecieResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
    }

    @Transactional
    public EspecieResponseDTO salvar(EspecieRequestDTO dto) {
        EspecieEntity especie = new EspecieEntity();
        especie.setNome(dto.nome().toUpperCase());
        especie.setInativo("N");
        return toDTO(repository.save(especie));
    }

    @Transactional
    public EspecieResponseDTO atualizar(Long id, EspecieRequestDTO dto) {
        EspecieEntity especie = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
        especie.setNome(dto.nome().toUpperCase());
        return toDTO(repository.save(especie));
    }

    @Transactional
    public EspecieResponseDTO inativar(Long id) {
        EspecieEntity especie = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
        especie.setInativo("S".equals(especie.getInativo()) ? "N" : "S");
        return toDTO(repository.save(especie));
    }

    private EspecieResponseDTO toDTO(EspecieEntity e) {
        return new EspecieResponseDTO(e.getId(), e.getNome(), e.getInativo());
    }
}
