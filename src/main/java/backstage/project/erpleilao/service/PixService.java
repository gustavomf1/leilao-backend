package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.PixRequestDTO;
import backstage.project.erpleilao.dtos.PixResponseDTO;
import backstage.project.erpleilao.entity.PixEntity;
import backstage.project.erpleilao.entity.UsuarioEntity;
import backstage.project.erpleilao.entity.enums.TipoChavePix;
import backstage.project.erpleilao.repository.PixRepository;
import backstage.project.erpleilao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PixService {

    @Autowired
    private PixRepository pixRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public PixResponseDTO cadastrar(PixRequestDTO dto) {
        UsuarioEntity usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PixEntity pix = new PixEntity();
        pix.setPix_tipo(dto.tipo());
        pix.setPix_chave(dto.chave());
        pix.setPix_usuario(usuario);

        return convertToResponseDTO(pixRepository.save(pix));
    }

    @Transactional(readOnly = true)
    public List<PixResponseDTO> listarPorUsuario(Long usuarioId) {
        return pixRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PixResponseDTO> listarPorUsuarioETipo(Long usuarioId, TipoChavePix tipo) {
        return pixRepository.findByUsuarioIdAndTipo(usuarioId, tipo).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluir(Long pixId) {
        PixEntity pix = pixRepository.findById(pixId)
                .orElseThrow(() -> new RuntimeException("Chave Pix não encontrada"));
        pixRepository.delete(pix);
    }

    private PixResponseDTO convertToResponseDTO(PixEntity pix) {
        return new PixResponseDTO(
                pix.getPix_id(),
                pix.getPix_tipo(),
                pix.getPix_chave(),
                pix.getPix_usuario().getUsu_id(),
                pix.getPix_usuario().getUsu_nome()
        );
    }
}
