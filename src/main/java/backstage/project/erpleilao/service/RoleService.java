package backstage.project.erpleilao.service;

import backstage.project.erpleilao.dtos.*;
import backstage.project.erpleilao.entity.PermissaoEntity;
import backstage.project.erpleilao.entity.RoleEntity;
import backstage.project.erpleilao.entity.UsuarioEntity;
import backstage.project.erpleilao.entity.enums.Acao;
import backstage.project.erpleilao.entity.enums.Ambiente;
import backstage.project.erpleilao.entity.enums.TipoUsuario;
import backstage.project.erpleilao.repository.RoleRepository;
import backstage.project.erpleilao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public RoleResponseDTO criar(RoleRequestDTO dto) {
        if (roleRepository.existsByNome(dto.nome())) {
            throw new RuntimeException("Já existe uma role com o nome: " + dto.nome());
        }

        RoleEntity role = new RoleEntity();
        role.setRole_nome(dto.nome());
        role.setRole_descricao(dto.descricao());
        aplicarPermissoes(role, dto.permissoes());

        RoleEntity salva = roleRepository.save(role);
        return new RoleResponseDTO(salva);
    }

    public List<RoleResponseDTO> listarTodas() {
        return roleRepository.findAll()
                .stream()
                .map(RoleResponseDTO::new)
                .toList();
    }

    public RoleResponseDTO buscarPorId(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));
        return new RoleResponseDTO(role);
    }

    @Transactional
    public RoleResponseDTO atualizar(Long id, RoleRequestDTO dto) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        if (dto.nome() != null) role.setRole_nome(dto.nome());
        if (dto.descricao() != null) role.setRole_descricao(dto.descricao());
        aplicarPermissoes(role, dto.permissoes());

        return new RoleResponseDTO(roleRepository.save(role));
    }

    @Transactional
    public void deletar(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role não encontrada");
        }
        roleRepository.deleteById(id);
    }

    @Transactional
    public UsuarioFuncionarioResponseDTO atribuirRoles(Long funcionarioId, AtribuirRolesDTO dto) {
        UsuarioEntity funcionario = usuarioRepository.findById(funcionarioId)
                .filter(u -> u.getUsu_tipo() == TipoUsuario.FUNCIONARIO)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        if (dto.isAdmin() != null) {
            funcionario.setUsu_is_admin(dto.isAdmin());
        }

        if (dto.roleIds() != null) {
            Set<RoleEntity> roles = new HashSet<>(roleRepository.findAllById(dto.roleIds()));
            funcionario.setUsu_roles(roles);
        }

        return new UsuarioFuncionarioResponseDTO(funcionario);
    }

    private void aplicarPermissoes(RoleEntity role, java.util.List<PermissaoDTO> permissoes) {
        role.getPermissoes().clear();
        if (permissoes != null) {
            for (PermissaoDTO p : permissoes) {
                PermissaoEntity pe = new PermissaoEntity();
                pe.setRole(role);
                pe.setAcao(Acao.valueOf(p.acao()));
                pe.setAmbiente(Ambiente.valueOf(p.ambiente()));
                role.getPermissoes().add(pe);
            }
        }
    }
}
