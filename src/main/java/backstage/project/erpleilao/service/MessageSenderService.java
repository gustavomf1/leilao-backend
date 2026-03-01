package backstage.project.erpleilao.service;
import backstage.project.erpleilao.dtos.*;
import backstage.project.erpleilao.messaging.client.EvolutionApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.random.RandomGenerator;

@Service
public class MessageSenderService {

    @Autowired
    private EvolutionApiClient evolutionApiClient;
    private final RandomGenerator random = RandomGenerator.getDefault();

    public MessageSenderService(EvolutionApiClient evolutionApiClient) {
        this.evolutionApiClient = evolutionApiClient;
    }

    // -------------------------------------------------------
    // Envios simples
    // -------------------------------------------------------

    public EvolutionResponseDTO sendText(SendTextMessageDTO dto) {
        return evolutionApiClient.sendTextMessage(dto.number(), dto.text(), dto.delay());
    }

    public EvolutionResponseDTO sendMedia(SendMediaMessageDTO dto) {
        return evolutionApiClient.sendMediaMessage(
                dto.number(), dto.url(), dto.caption(), dto.mediatype(), dto.mimeType()
        );
    }

    // -------------------------------------------------------
    // Bulk texto
    // -------------------------------------------------------

    @Async("bulkSenderExecutor")
    public CompletableFuture<BulkResultDTO> sendBulk(SendBulkMessageDTO dto) {

        var results = new ArrayList<BulkResultDTO.ItemResult>();
        int sucessos = 0, falhas = 0;
        var contatos = dto.contatos();

        for (int i = 0; i < contatos.size(); i++) {
            var contato = contatos.get(i);
            try {
                var texto    = personalizar(dto.text(), contato.nome());
                var response = evolutionApiClient.sendTextMessage(contato.number(), texto, 1200);
                var id       = extractId(response);

                results.add(new BulkResultDTO.ItemResult(contato.number(), true, id, null));
                sucessos++;

            } catch (Exception e) {
                results.add(new BulkResultDTO.ItemResult(contato.number(), false, null, e.getMessage()));
                falhas++;
            }

            if (i < contatos.size() - 1) waitDelay(dto.delayMinMs(), dto.delayMaxMs());
        }

        return CompletableFuture.completedFuture(new BulkResultDTO(contatos.size(), sucessos, falhas, results));
    }

    // -------------------------------------------------------
    // Bulk mídia
    // -------------------------------------------------------

    /**
     * Envia a mesma mídia para uma lista de contatos, com:
     * - Caption personalizada por contato via {nome}
     * - Delay aleatório entre envios para evitar banimento
     * - Execução assíncrona em Virtual Thread
     */
    @Async("bulkSenderExecutor")
    public CompletableFuture<BulkResultDTO> sendBulkMedia(SendBulkMediaMessageDTO dto) {
        var results = new ArrayList<BulkResultDTO.ItemResult>();
        int sucessos = 0, falhas = 0;
        var contatos = dto.contatos();

        for (int i = 0; i < contatos.size(); i++) {
            var contact = contatos.get(i);
            try {
                var caption  = personalizar(dto.caption(), contact.nome());
                var response = evolutionApiClient.sendMediaMessage(
                        contact.number(),
                        dto.url(),
                        caption,
                        dto.mediatype(),
                        dto.mimeType()
                );
                var id = extractId(response);

                results.add(new BulkResultDTO.ItemResult(contact.number(), true, id, null));
                sucessos++;

            } catch (Exception e) {
                results.add(new BulkResultDTO.ItemResult(contact.number(), false, null, e.getMessage()));
                falhas++;
            }

            if (i < contatos.size() - 1) waitDelay(dto.delayMinMs(), dto.delayMaxMs());
        }

        return CompletableFuture.completedFuture(new BulkResultDTO(contatos.size(), sucessos, falhas, results));
    }

    // -------------------------------------------------------
    // Helpers
    // -------------------------------------------------------

    private String personalizar(String texto, String nome) {
        if (texto == null) return null;
        return (nome != null && !nome.isBlank())
                ? texto.replace("{nome}", nome)
                : texto.replace("{nome}", "");
    }

    private String extractId(EvolutionResponseDTO response) {
        return (response != null && response.messageKey() != null) ? response.messageKey().id() : null;
    }

    private void waitDelay(int minMs, int maxMs) {
        try {
            int delay = minMs + random.nextInt(maxMs - minMs);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}