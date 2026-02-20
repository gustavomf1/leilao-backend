package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Schema(description = "Payload para envio de mensagem com mídia em massa via WhatsApp")
public record SendBulkMediaMessageDTO(

        @Schema(description = "Lista de contatos que receberão a mídia")
        @NotEmpty(message = "A lista de contatos não pode ser vazia")
        @Valid
        List<Contact> contatos,

        @Schema(description = "URL pública ou string base64 da mídia", example = "https://exemplo.com/catalogo.pdf")
        @NotBlank(message = "A URL ou base64 da mídia é obrigatória")
        String url,

        @Schema(description = "Legenda opcional exibida abaixo da mídia", example = "Nosso catálogo atualizado!")
        String caption,

        @Schema(description = "Tipo da mídia (ex: image, video, document, audio)", example = "document")
        @NotBlank(message = "O tipo de mídia é obrigatório")
        String mediatype,

        @Schema(description = "MIME type do arquivo", example = "application/pdf")
        @NotBlank(message = "O tipo de mídia é obrigatório")
        String mimeType,

        @Schema(description = "Delay mínimo em ms entre os envios para evitar banimento. Padrão: 3000ms", example = "3000", defaultValue = "3000")
        @Min(value = 1000, message = "O delay mínimo é 1000ms para evitar banimento")
        Integer delayMinMs,

        @Schema(description = "Delay máximo em ms entre os envios. Padrão: 8000ms", example = "8000", defaultValue = "8000")
        @Max(value = 30000, message = "O delay máximo é 30000ms")
        Integer delayMaxMs
) {
    public SendBulkMediaMessageDTO {
        delayMinMs = (delayMinMs == null) ? 3000 : delayMinMs;
        delayMaxMs = (delayMaxMs == null) ? 8000 : delayMaxMs;
    }

    @Schema(description = "Contato destinatário da mídia em massa")
    public record Contact(
            @Schema(description = "Número do contato com DDI+DDD (apenas dígitos)", example = "5511988887777")
            @NotBlank(message = "O número do contato é obrigatório")
            String number,

            @Schema(description = "Nome do contato (opcional)", example = "Maria Oliveira")
            String nome
    ) {}
}