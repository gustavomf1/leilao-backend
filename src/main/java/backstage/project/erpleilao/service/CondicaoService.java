package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.CondicaoRequestDTO;
import backstage.project.erpleilao.dtos.CondicaoResponseDTO;
import backstage.project.erpleilao.entity.CondicaoEntity;
import backstage.project.erpleilao.repository.CondicaoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondicaoService {

    @Autowired
    private CondicaoRepository repository;

    @Transactional(readOnly = true)
    public List<CondicaoResponseDTO> listar() {
        return repository.findAllAtivas().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CondicaoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .filter(c -> "N".equals(c.getInativo()))
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Condição não encontrada ou inativa"));
    }

    @Transactional
    public CondicaoResponseDTO salvar(CondicaoRequestDTO dto) {
        CondicaoEntity condicaoEntity = new CondicaoEntity();
        condicaoEntity.setTipo(dto.tipo());
        condicaoEntity.setDescricao(dto.descricao());
        condicaoEntity.setInativo("N");
        return convertToResponseDTO(repository.save(condicaoEntity));
    }

    @Transactional
    public CondicaoResponseDTO atualizar(Long id, CondicaoRequestDTO dto) {
        CondicaoEntity condicaoEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condição não encontrada"));
        condicaoEntity.setTipo(dto.tipo());
        condicaoEntity.setDescricao(dto.descricao());
        return convertToResponseDTO(repository.save(condicaoEntity));
    }

    @Transactional
    public void excluir(Long id) {
        CondicaoEntity condicaoEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condição não encontrada"));
        condicaoEntity.setInativo("S"); // Exclusão Lógica
        repository.save(condicaoEntity);
    }

    private CondicaoResponseDTO convertToResponseDTO(CondicaoEntity condicaoEntity) {
        return new CondicaoResponseDTO(
                condicaoEntity.getId(),
                condicaoEntity.getTipo(),
                condicaoEntity.getDescricao(),
                condicaoEntity.getInativo()
        );
    }
}
