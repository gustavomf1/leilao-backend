package backstage.project.erpleilao.dtos;

import backstage.project.erpleilao.entity.enums.TipoUsuario;

public record UsuarioFuncionarioUpdateDTO(
        String nome,
        TipoUsuario tipo
) {}
