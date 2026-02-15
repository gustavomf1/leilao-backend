package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.*;
import backstage.project.erpleilao.entity.Usuario;
import backstage.project.erpleilao.entity.enums.TipoUsuario;
import backstage.project.erpleilao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        funcionario.setUsu_senha(senhaCriptografada);

        Usuario salvo = repository.save(funcionario);
        return new UsuarioFuncionarioResponseDTO(salvo);
    }

    @Transactional
    public UsuarioClienteResponseDTO salvarCliente(UsuarioClienteDTO dto) {
        if (repository.existsByUsu_email(dto.email())) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }
        if (repository.existsByUsu_cpf(dto.cpf())) {
            throw new RuntimeException("CPF já cadastrado no sistema.");
        }

        Usuario cliente = new Usuario();
        cliente.setUsu_nome(dto.nome());
        cliente.setUsu_email(dto.email());
        cliente.setUsu_cpf(dto.cpf());
        cliente.setUsu_telefone(dto.telefone());
        cliente.setUsu_cidade(dto.cidade());
        cliente.setUsu_uf(dto.uf());
        cliente.setUsu_rg(dto.rg());
        cliente.setUsu_tipo(TipoUsuario.CLIENTE);
        cliente.setUsu_inativo("N");

        Usuario salvo = repository.save(cliente);
        return new UsuarioClienteResponseDTO(salvo);
    }

    public List<UsuarioFuncionarioResponseDTO> listarFuncionarios() {
        return repository.findByTipo(TipoUsuario.FUNCIONARIO)
                .stream()
                .map(UsuarioFuncionarioResponseDTO::new)
                .toList();
    }

    public List<UsuarioClienteResponseDTO> listarClientes() {
        return repository.findByTipo(TipoUsuario.CLIENTE)
                .stream()
                .map(UsuarioClienteResponseDTO::new)
                .toList();
    }

    public UsuarioFuncionarioResponseDTO buscarFuncionarioPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .filter(u -> u.getUsu_tipo() == TipoUsuario.FUNCIONARIO)
                .orElseThrow(() -> new RuntimeException("ID do funcionário não encontrado"));
        return new UsuarioFuncionarioResponseDTO(usuario);
    }

    public UsuarioClienteResponseDTO buscarClientePorId(Long id) {
        Usuario usuario = repository.findById(id)
                .filter(u -> u.getUsu_tipo() == TipoUsuario.CLIENTE)
                .orElseThrow(() -> new RuntimeException("ID do cliente não encontrado"));
        return new UsuarioClienteResponseDTO(usuario);
    }

    @Transactional
    public UsuarioFuncionarioResponseDTO atualizarFuncionario(Long id, UsuarioFuncionarioUpdateDTO dto) {
        Usuario funcionario = repository.findById(id)
                .filter(u -> u.getUsu_tipo() == TipoUsuario.FUNCIONARIO)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));
        if (dto.nome() != null) {
            funcionario.setUsu_nome(dto.nome());
        }
        if (dto.tipo() != null) {
            funcionario.setUsu_tipo(dto.tipo());
        }
        return new UsuarioFuncionarioResponseDTO(funcionario);
    }

    @Transactional
    public UsuarioClienteResponseDTO atualizarCliente(Long id, UsuarioClienteUpdateDTO dto) {
        Usuario cliente = repository.findById(id)
                .filter(u -> u.getUsu_tipo() == TipoUsuario.CLIENTE)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        if (dto.nome() != null) cliente.setUsu_nome(dto.nome());
        if (dto.telefone() != null) cliente.setUsu_telefone(dto.telefone());
        if (dto.cidade() != null) cliente.setUsu_cidade(dto.cidade());
        if (dto.uf() != null) cliente.setUsu_uf(dto.uf());
        if (dto.rg() != null) cliente.setUsu_rg(dto.rg());

        return new UsuarioClienteResponseDTO(cliente);
    }

    @Transactional
    public void inativarUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID de usuário não encontrado"));

        usuario.setUsu_inativo("S");
    }
}
