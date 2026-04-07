package backstage.project.erpleilao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void enviarRecuperacaoSenha(String destinatario, String token) {
        String link = frontendUrl + "/#/redefinir-senha?token=" + token;

        String corpo = """
                <div style="font-family: Arial, sans-serif; max-width: 500px; margin: 0 auto;">
                  <h2 style="color: #321fdb;">Redefinição de senha</h2>
                  <p>Recebemos uma solicitação para redefinir a senha da sua conta.</p>
                  <p>Clique no botão abaixo para criar uma nova senha. O link é válido por <strong>30 minutos</strong>.</p>
                  <div style="text-align: center; margin: 32px 0;">
                    <a href="%s"
                       style="background-color: #321fdb; color: white; padding: 12px 28px;
                              text-decoration: none; border-radius: 6px; font-weight: bold;">
                      Redefinir senha
                    </a>
                  </div>
                  <p style="color: #8a93a2; font-size: 0.85rem;">
                    Se você não solicitou a redefinição, ignore este email. Sua senha permanece a mesma.
                  </p>
                </div>
                """.formatted(link);

        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("Redefinição de senha — JA Leilões");
            helper.setText(corpo, true);
            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar email de recuperação");
        }
    }
}
