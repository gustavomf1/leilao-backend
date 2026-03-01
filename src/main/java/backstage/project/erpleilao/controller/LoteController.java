package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.LoteDisplayDTO;
import backstage.project.erpleilao.dtos.LoteRequestDTO;
import backstage.project.erpleilao.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lotes")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Lotes", description = "Gestão de lotes do leilão")
public class LoteController {

    @Autowired
    private LoteService service;

    @PostMapping
    @Operation(summary = "Cadastra um novo lote", description = "Cria um lote e dispara notificação via Redis/WebSocket",
            security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<LoteDisplayDTO> cadastrar(@RequestBody @Valid LoteRequestDTO dados) {
        var novoLote = service.cadastrar(dados);
        return ResponseEntity.ok(novoLote);
    }

    @GetMapping
    @Operation(summary = "Lista todos os lotes", description = "Retorna todos os lotes cadastrados no sistema")
    public ResponseEntity<List<LoteDisplayDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}