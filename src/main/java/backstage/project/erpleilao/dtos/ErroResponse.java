package backstage.project.erpleilao.dtos;

public record ErroResponse(
        int status,
        String mensagem,
        long timestamp
) {}
