package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Payload para envio de mensagem com mídia via WhatsApp")
public record SendMediaMessageDTO(

        @Schema(description = "Número do destinatário com DDI+DDD (apenas dígitos)", example = "5511999999999")
        @NotBlank(message = "O número é obrigatório")
        @Pattern(regexp = "\\d{10,15}", message = "Número inválido. Use apenas dígitos (ex: 5511999999999)")
        String number,

        @Schema(description = "Tipo da mídia (ex: image, video, document, audio)", example = "image")
        @NotBlank(message = "O tipo de mídia é obrigatório")
        String mediatype,

        @Schema(description = "MIME type do arquivo", example = "image/jpeg")
        @NotBlank(message = "O tipo de mídia é obrigatório")
        String mimeType,

        @Schema(description = "Nome do arquivo com extensão", example = "foto.jpg")
        @NotBlank(message = "O nome do arquivo é obrigatório")
        String fileName,

        @Schema(description = "URL pública ou string base64 da mídia", example = "https://exemplo.com/imagem.jpg")
        @NotBlank(message = "A URL ou base64 da mídia é obrigatória")
        String url,

        @Schema(description = "Legenda opcional exibida abaixo da mídia", example = "Confira o documento anexo!")
        String caption
) {}