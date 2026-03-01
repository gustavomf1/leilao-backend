package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Payload para envio de mensagem de texto via WhatsApp")
public record SendTextMessageDTO(

        @Schema(description = "Número do destinatário com DDI+DDD (apenas dígitos)", example = "5511999999999")
        @NotBlank(message = "O número é obrigatório")
        @Pattern(regexp = "\\d{10,15}", message = "Número inválido. Use apenas dígitos (ex: 5511999999999)")
        String number,

        @Schema(description = "Texto da mensagem a ser enviada", example = "Olá! Tudo bem?")
        @NotBlank(message = "O texto da mensagem é obrigatório")
        String text,

        @Schema(description = "Delay em milissegundos antes do envio. Padrão: 1200ms", example = "1200", defaultValue = "1200")
        Integer delay
) {
    public SendTextMessageDTO {
        delay = (delay == null) ? 1200 : delay;
    }
}