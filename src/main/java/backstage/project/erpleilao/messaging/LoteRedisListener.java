package backstage.project.erpleilao.messaging;

import backstage.project.erpleilao.dtos.LoteDisplayDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message; // IMPORTANTE: Usar este pacote
import org.springframework.data.redis.connection.MessageListener; // Interface necessária
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoteRedisListener implements MessageListener { // Adicione a interface aqui

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            LoteDisplayDTO dto = objectMapper.readValue(message.getBody(), LoteDisplayDTO.class);
            messagingTemplate.convertAndSend("/topic/lotes", dto);
        } catch (IOException e) {
            System.err.println("Erro ao processar mensagem do Redis: " + e.getMessage());
        }
    }
}