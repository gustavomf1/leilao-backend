package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.Usuario;

public record UsuarioClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String cidade,
        String uf
) {
    public UsuarioClienteResponseDTO(Usuario usuario) {
        this(usuario.getUsu_id(), usuario.getUsu_nome(), usuario.getUsu_email(),
                usuario.getUsu_cpf(), usuario.getUsu_telefone(), usuario.getUsu_cidade(),
                usuario.getUsu_uf());
    }
}
