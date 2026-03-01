package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.*;
import backstage.project.erpleilao.service.MessageSenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/messages")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Mensagens WhatsApp", description = "Endpoints para envio de mensagens de texto e mídia via WhatsApp usando a Evolution API")
public class SendMessageController {

    @Autowired
    private MessageSenderService messageSenderService;

    private static final Logger log = LoggerFactory.getLogger(SendMessageController.class);

    // -------------------------------------------------------------------------
    // Texto simples
    // -------------------------------------------------------------------------

    @Operation(
            summary = "Enviar mensagem de texto",
            description = "Envia uma mensagem de texto para um único número de WhatsApp via Evolution API."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mensagem enviada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EvolutionResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao comunicar com a Evolution API",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @PostMapping("/send/text")
    public ResponseEntity<ApiResponseDTO<EvolutionResponseDTO>> sendText(
            @RequestBody(
                    description = "Dados da mensagem de texto a ser enviada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SendTextMessageDTO.class))
            )
            @org.springframework.web.bind.annotation.RequestBody SendTextMessageDTO dto) {

        log.info(String.valueOf(dto));
        var response = messageSenderService.sendText(dto);
        return ResponseEntity.ok(ApiResponseDTO.ok("Mensagem enviada com sucesso", response));
    }

    // -------------------------------------------------------------------------
    // Mídia simples
    // -------------------------------------------------------------------------

    @Operation(
            summary = "Enviar mensagem com mídia",
            description = "Envia uma mensagem com anexo de mídia (imagem, vídeo, documento ou áudio) para um único número de WhatsApp."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mídia enviada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EvolutionResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao comunicar com a Evolution API",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @PostMapping("/send/media")
    public ResponseEntity<ApiResponseDTO<EvolutionResponseDTO>> sendMedia(
            @RequestBody(
                    description = "Dados da mensagem com mídia a ser enviada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SendMediaMessageDTO.class))
            )
            @org.springframework.web.bind.annotation.RequestBody SendMediaMessageDTO dto) {

        var response = messageSenderService.sendMedia(dto);
        return ResponseEntity.ok(ApiResponseDTO.ok("Mídia enviada com sucesso", response));
    }

    // -------------------------------------------------------------------------
    // Texto em massa
    // -------------------------------------------------------------------------

    @Operation(
            summary = "Enviar mensagem de texto em massa",
            description = """
            Envia uma mensagem de texto para múltiplos contatos de forma assíncrona.
            Um delay aleatório entre `delayMinMs` e `delayMaxMs` é aplicado entre cada envio
            para evitar bloqueios pela plataforma WhatsApp.
            """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Envio em massa aceito e em processamento",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos — lista vazia, delay fora do intervalo permitido, etc.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno durante o processamento do envio em massa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @PostMapping("/send/bulk")
    public ResponseEntity<ApiResponseDTO<String>> sendBulk(
            @RequestBody(
                    description = "Lista de contatos e configurações do envio em massa de texto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SendBulkMessageDTO.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody SendBulkMessageDTO dto) {

        messageSenderService.sendBulk(dto);
        var msg = "Mensagem enviada para %d contatos.".formatted(dto.contatos().size());
        return ResponseEntity.accepted().body(ApiResponseDTO.ok(msg, null));
    }

    // -------------------------------------------------------------------------
    // Mídia em massa
    // -------------------------------------------------------------------------

    @Operation(
            summary = "Enviar mensagem com mídia em massa",
            description = """
            Envia uma mensagem com anexo de mídia para múltiplos contatos de forma assíncrona.
            Um delay aleatório entre `delayMinMs` e `delayMaxMs` é aplicado entre cada envio
            para evitar bloqueios pela plataforma WhatsApp.
            """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Envio em massa de mídia aceito e em processamento",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos — lista vazia, MIME type ausente, delay fora do intervalo, etc.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno durante o processamento do envio em massa de mídia",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @PostMapping("/send/bulk/media")
    public ResponseEntity<ApiResponseDTO<String>> sendBulkMedia(
            @RequestBody(
                    description = "Lista de contatos e configurações do envio em massa de mídia",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SendBulkMediaMessageDTO.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody SendBulkMediaMessageDTO dto) {

        messageSenderService.sendBulkMedia(dto);
        var msg = "Mensagem enviada para %d contatos.".formatted(dto.contatos().size());
        return ResponseEntity.accepted().body(ApiResponseDTO.ok(msg, null));
    }
}