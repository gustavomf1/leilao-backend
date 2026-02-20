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
        FazendaEntity fazendaEntity = new FazendaEntity();
        mapRequestToEntity(dto, fazendaEntity);
        return convertToResponseDTO(repository.save(fazendaEntity));
    }

    @Transactional
    public FazendaResponseDTO atualizar(Long id, FazendaRequestDTO dto) {
        FazendaEntity fazendaEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada"));
        mapRequestToEntity(dto, fazendaEntity);
        return convertToResponseDTO(repository.save(fazendaEntity));
    }

    @Transactional
    public void excluir(Long id) {
        FazendaEntity fazendaEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada para exclusão"));

        fazendaEntity.setInativo("S");
        repository.save(fazendaEntity);
    }

    private void mapRequestToEntity(FazendaRequestDTO dto, FazendaEntity entity) {
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

    private FazendaResponseDTO convertToResponseDTO(FazendaEntity fazendaEntity) {
        return new FazendaResponseDTO(
                fazendaEntity.getId(),
                fazendaEntity.getNome(),
                fazendaEntity.getInscricao(),
                fazendaEntity.getCnpj(),
                fazendaEntity.getUf(),
                fazendaEntity.getCidade(),
                fazendaEntity.getInativo(),
                fazendaEntity.getDt_criacao(),
                fazendaEntity.getFaz_titular().getUsu_nome()
        );
    }
}