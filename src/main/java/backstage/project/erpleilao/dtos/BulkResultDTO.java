package backstage.project.erpleilao.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Resultado consolidado de um envio em massa")
public record BulkResultDTO(

        @Schema(description = "Total de contatos processados", example = "50")
        int total,

        @Schema(description = "Quantidade de envios bem-sucedidos", example = "47")
        int sucessos,

        @Schema(description = "Quantidade de envios que falharam", example = "3")
        int falhas,

        @Schema(description = "Lista detalhada com o resultado individual de cada envio")
        List<ItemResult> resultados
) {
    @Schema(description = "Resultado individual do envio para um contato")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ItemResult(
            @Schema(description = "Número do contato", example = "5511999999999")
            String number,

            @Schema(description = "Indica se o envio foi bem-sucedido", example = "true")
            boolean success,

            @Schema(description = "ID da mensagem gerado pelo WhatsApp (presente apenas em caso de sucesso)", example = "3EB0C767D8B5A23F1D4E")
            String messageId,

            @Schema(description = "Descrição do erro ocorrido (presente apenas em caso de falha)", example = "Timeout ao conectar com a Evolution API")
            String error
    ) {}
}