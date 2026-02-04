package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.UsuarioFuncionarioDTO;
import backstage.project.erpleilao.dtos.UsuarioFuncionarioResponseDTO;
import backstage.project.erpleilao.entity.Usuario;
import backstage.project.erpleilao.entity.enums.TipoUsuario;
import backstage.project.erpleilao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public UsuarioFuncionarioResponseDTO salvarFuncionario(UsuarioFuncionarioDTO dto) {
        if (repository.existsByUsu_email(dto.email())) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }
        if (repository.existsByUsu_cpf(dto.cpf())) {
            throw new RuntimeException("CPF já cadastrado no sistema.");
        }

        Usuario funcionario = new Usuario();
        funcionario.setUsu_nome(dto.nome());
        funcionario.setUsu_email(dto.email());
        funcionario.setUsu_cpf(dto.cpf());
        funcionario.setUsu_senha(dto.senha());
        funcionario.setUsu_tipo(TipoUsuario.FUNCIONARIO);
        funcionario.setUsu_inativo("N");

        Usuario salvo = repository.save(funcionario);
        return new UsuarioFuncionarioResponseDTO(salvo);
    }
}
