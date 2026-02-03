package backstage.project.erpleilao.dtos;

public record UsuarioClienteDTO(
        String nome,
        String email,
        String cpf,
        String telefone,
        String cidade,
        String uf,
        String rg
) {}
