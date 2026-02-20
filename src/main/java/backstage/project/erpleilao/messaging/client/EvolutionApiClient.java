package backstage.project.erpleilao.messaging.client;

import backstage.project.erpleilao.dtos.EvolutionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class EvolutionApiClient {

    private static final Logger log = LoggerFactory.getLogger(EvolutionApiClient.class);

    private final RestTemplate restTemplate;

    public EvolutionApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${evolution.api.url}")
    private String apiUrl;

    @Value("${evolution.api.key}")
    private String apiKey;

    @Value("${evolution.api.instance}")
    private String instance;

    public EvolutionResponseDTO sendTextMessage(String number, String text, int delay) {
        var url = "%s/message/sendText/%s".formatted(apiUrl, instance);
        var payload = Map.of(
                "number", formatNumber(number),
                "text", text,
                "delay", delay
        );
        return post(url, payload);
    }

    public EvolutionResponseDTO sendMediaMessage(
            String number, String url, String caption, String mediatype, String mimeType
    ) {
        var fullUrl = "%s/message/sendMedia/%s".formatted(apiUrl, instance);

        var payload = new HashMap<String, Object>();
        payload.put("number", formatNumber(number));
        payload.put("media", url);
        payload.put("mimeType", mimeType);
        payload.put("mediatype", mediatype);
        if (caption != null && !caption.isBlank()) {
            payload.put("caption", caption);
        }
        return post(fullUrl, payload);
    }

    /**
     * Formata o número para o padrão aceito pela Evolution API.
     * Ex: "5511999999999" → "5511999999999@s.whatsapp.net"
     */
    private String formatNumber(String number) {
        var cleaned = number.replaceAll("[^0-9]", "");
        return cleaned.contains("@") ? cleaned : cleaned + "@s.whatsapp.net";
    }

    private EvolutionResponseDTO post(String url, Object body) {
        try {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("apikey", apiKey);

            var entity = new HttpEntity<>(body, headers);
            log.info(String.valueOf(entity));
            var response = restTemplate.exchange(url, HttpMethod.POST, entity, EvolutionResponseDTO.class);
            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.info(String.valueOf(e));
            throw new RuntimeException("Erro ao enviar mensagem: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.info(String.valueOf(e));
            throw new RuntimeException("Erro inesperado ao enviar mensagem", e); // <-- RuntimeException
        }
    }
}