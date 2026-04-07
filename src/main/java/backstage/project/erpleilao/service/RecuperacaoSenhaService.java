package backstage.project.erpleilao.service;

import backstage.project.erpleilao.entity.RecuperacaoSenhaEntity;
import backstage.project.erpleilao.entity.UsuarioEntity;
import backstage.project.erpleilao.repository.RecuperacaoSenhaRepository;
import backstage.project.erpleilao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecuperacaoSenhaService {

    @Autowired
    private RecuperacaoSenhaRepository recuperacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public void solicitarRecuperacao(String email) {
        // 1. busca o usuário
        UsuarioEntity usuario = (UsuarioEntity) usuarioRepository.findByUsuEmail(email);
        if (usuario == null) throw new RuntimeException("Email não encontrado");

        // 2. gera o token
        String token = UUID.randomUUID().toString();

        // 3. salva no banco
        RecuperacaoSenhaEntity recuperacao = new RecuperacaoSenhaEntity();
        recuperacao.setUsuario(usuario);
        recuperacao.setToken(token);
        recuperacao.setDataExpiracao(LocalDateTime.now().plusMinutes(30));
        recuperacaoRepository.save(recuperacao);

        // 4. envia email com o link de redefinição
        emailService.enviarRecuperacaoSenha(email, token);
    }

    public void validarToken(String token) {
        RecuperacaoSenhaEntity recuperacao = recuperacaoRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (LocalDateTime.now().isAfter(recuperacao.getDataExpiracao())) {
            throw new RuntimeException("Token expirado");
        }

        if ("S".equals(recuperacao.getUsado())) {
            throw new RuntimeException("Token já utilizado");
        }
    }

    public void redefinirSenha(String token, String novaSenha) {
        // 1. busca o token
        RecuperacaoSenhaEntity recuperacao = recuperacaoRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        // 2. verifica expiração
        if (LocalDateTime.now().isAfter(recuperacao.getDataExpiracao())) {
            throw new RuntimeException("Token expirado");
        }

        // 3. verifica se já foi usado
        if ("S".equals(recuperacao.getUsado())) {
            throw new RuntimeException("Token já utilizado");
        }

        // 4. atualiza a senha e marca como usado
        UsuarioEntity usuario = recuperacao.getUsuario();
        usuario.setUsu_senha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        recuperacao.setUsado("S");
        recuperacaoRepository.save(recuperacao);
    }
}
