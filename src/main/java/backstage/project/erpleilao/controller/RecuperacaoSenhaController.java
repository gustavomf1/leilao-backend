package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.RedefinirSenhaDTO;
import backstage.project.erpleilao.dtos.SolicitarRecuperacaoDTO;
import backstage.project.erpleilao.service.RecuperacaoSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/senha")
public class RecuperacaoSenhaController {

    @Autowired
    private RecuperacaoSenhaService service;

    @PostMapping("/solicitar")
    public ResponseEntity<Void> solicitar(@RequestBody SolicitarRecuperacaoDTO dto) {
        service.solicitarRecuperacao(dto.email());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validar")
    public ResponseEntity<Void> validar(@RequestParam String token) {
        service.validarToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir")
    public ResponseEntity<Void> redefinir(@RequestBody RedefinirSenhaDTO dto) {
        service.redefinirSenha(dto.token(), dto.novaSenha());
        return ResponseEntity.ok().build();
    }
}