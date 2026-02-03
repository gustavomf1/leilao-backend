package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.Usuario;
import backstage.project.erpleilao.entity.enums.TipoUsuario;

import java.time.LocalDateTime;

public record UsuarioFuncionarioResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        TipoUsuario tipo,
        String inativo,
        LocalDateTime dataCriacao
) {
    public UsuarioFuncionarioResponseDTO(Usuario usuario) {
        this(
                usuario.getUsu_id(),
                usuario.getUsu_nome(),
                usuario.getUsu_email(),
                usuario.getUsu_cpf(),
                usuario.getUsu_tipo(),
                usuario.getUsu_inativo(),
                usuario.getUsu_dt_criacao()
        );
    }
}
