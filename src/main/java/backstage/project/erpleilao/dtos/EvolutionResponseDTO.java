package backstage.project.erpleilao.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta retornada pela API Evolution após o envio de uma mensagem")
@JsonIgnoreProperties(ignoreUnknown = true)
public record EvolutionResponseDTO(

        @Schema(description = "Identificadores da mensagem enviada")
        MessageKey messageKey,

        @Schema(description = "Status do envio retornado pela Evolution API", example = "PENDING")
        String status,

        @Schema(description = "Mensagem descritiva retornada pela Evolution API", example = "Message sent successfully")
        String message
) {
    @Schema(description = "Chave identificadora da mensagem no WhatsApp")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MessageKey(
            @Schema(description = "JID remoto do destinatário", example = "5511999999999@s.whatsapp.net")
            String remoteJid,

            @Schema(description = "Indica se a mensagem foi enviada pelo próprio número", example = "true")
            Boolean fromMe,

            @Schema(description = "ID único da mensagem no WhatsApp", example = "3EB0C767D8B5A23F1D4E")
            String id
    ) {}
}