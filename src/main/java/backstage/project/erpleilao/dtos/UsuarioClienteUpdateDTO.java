package backstage.project.erpleilao.dtos;

public record UsuarioClienteUpdateDTO(
        String nome,
        String telefone,
        String cidade,
        String uf,
        String rg
) {}
