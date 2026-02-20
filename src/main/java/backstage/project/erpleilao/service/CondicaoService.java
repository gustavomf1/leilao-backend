package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.CondicaoRequestDTO;
import backstage.project.erpleilao.dtos.CondicaoResponseDTO;
import backstage.project.erpleilao.entity.Condicao;
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
        Condicao condicao = new Condicao();
        condicao.setTipo(dto.tipo());
        condicao.setDescricao(dto.descricao());
        condicao.setInativo("N");
        return convertToResponseDTO(repository.save(condicao));
    }

    @Transactional
    public CondicaoResponseDTO atualizar(Long id, CondicaoRequestDTO dto) {
        Condicao condicao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condição não encontrada"));
        condicao.setTipo(dto.tipo());
        condicao.setDescricao(dto.descricao());
        return convertToResponseDTO(repository.save(condicao));
    }

    @Transactional
    public void excluir(Long id) {
        Condicao condicao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condição não encontrada"));
        condicao.setInativo("S"); // Exclusão Lógica
        repository.save(condicao);
    }

    private CondicaoResponseDTO convertToResponseDTO(Condicao condicao) {
        return new CondicaoResponseDTO(
                condicao.getId(),
                condicao.getTipo(),
                condicao.getDescricao(),
                condicao.getInativo()
        );
    }
}
