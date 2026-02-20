package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.List;

@Schema(description = "Payload para envio de mensagem de texto em massa via WhatsApp")
public record SendBulkMessageDTO(

        @Schema(description = "Lista de contatos que receberão a mensagem")
        @NotEmpty(message = "A lista de contatos não pode ser vazia")
        List<Contact> contatos,

        @Schema(description = "Texto da mensagem a ser enviada para todos os contatos", example = "Olá, temos novidades!")
        @NotBlank(message = "O texto da mensagem é obrigatório")
        String text,

        @Schema(description = "Delay mínimo em ms entre os envios para evitar banimento. Padrão: 3000ms", example = "3000", defaultValue = "3000")
        @Min(value = 1000, message = "O delay mínimo é 1000ms para evitar banimento")
        Integer delayMinMs,

        @Schema(description = "Delay máximo em ms entre os envios. Padrão: 8000ms", example = "8000", defaultValue = "8000")
        @Max(value = 30000, message = "O delay máximo é 30000ms")
        Integer delayMaxMs
) {
    public SendBulkMessageDTO {
        delayMinMs = (delayMinMs == null) ? 3000 : delayMinMs;
        delayMaxMs = (delayMaxMs == null) ? 8000 : delayMaxMs;
    }

    @Schema(description = "Contato destinatário da mensagem em massa")
    public record Contact(
            @Schema(description = "Número do contato com DDI+DDD (apenas dígitos)", example = "5511988887777")
            @NotBlank(message = "O número do contato é obrigatório")
            String number,

            @Schema(description = "Nome do contato (opcional, para personalização futura)", example = "João Silva")
            String nome
    ) {}
}