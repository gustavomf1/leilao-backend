package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.TaxaRequestDTO;
import backstage.project.erpleilao.dtos.TaxaResponseDTO;
import backstage.project.erpleilao.entity.TaxaComissaoEntity;
import backstage.project.erpleilao.repository.TaxaComissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxaComissaoService {
    @Autowired
    private TaxaComissaoRepository repository;

    @Transactional(readOnly = true)
    public List<TaxaResponseDTO> listar() {
        return repository.findAllAtivas().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaxaResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .filter(t -> "N".equals(t.getInativo()))
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Taxa não encontrada ou inativa"));
    }

    @Transactional
    public TaxaResponseDTO salvar(TaxaRequestDTO dto) {
        TaxaComissaoEntity taxa = new TaxaComissaoEntity();
        taxa.setPorcentagem(dto.porcentagem());
        taxa.setTipoCliente(dto.tipoCliente());
        taxa.setInativo("N");
        return convertToResponseDTO(repository.save(taxa));
    }

    @Transactional
    public TaxaResponseDTO atualizar(Long id, TaxaRequestDTO dto) {
        TaxaComissaoEntity taxa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taxa não encontrada"));
        taxa.setPorcentagem(dto.porcentagem());
        taxa.setTipoCliente(dto.tipoCliente());
        return convertToResponseDTO(repository.save(taxa));
    }

    @Transactional
    public void excluir(Long id) {
        TaxaComissaoEntity taxa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taxa não encontrada"));
        taxa.setInativo("S");
        repository.save(taxa);
    }

    private TaxaResponseDTO convertToResponseDTO(TaxaComissaoEntity taxa) {
        return new TaxaResponseDTO(
                taxa.getId(),
                taxa.getPorcentagem(),
                taxa.getTipoCliente(),
                taxa.getInativo()
        );
    }
}
