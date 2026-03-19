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
@RequestMapping("api/lote")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Lotes", description = "Gestão de lotes do leilão")
public class LoteController {

    @Autowired
    private LoteService service;

    @PostMapping
    @Operation(summary = "Cadastra um novo lote", description = "Cria um lote e dispara notificação via Redis/WebSocket")
    public ResponseEntity<LoteDisplayDTO> cadastrar(@RequestBody @Valid LoteRequestDTO dados) {
        var novoLote = service.cadastrar(dados);
        return ResponseEntity.ok(novoLote);
    }

    @GetMapping
    @Operation(summary = "Lista todos os lotes", description = "Retorna todos os lotes cadastrados no sistema")
    public ResponseEntity<List<LoteDisplayDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um lote", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<LoteDisplayDTO> atualizar(@PathVariable Long id, @RequestBody @Valid LoteRequestDTO dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um lote por ID", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<LoteDisplayDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um lote", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}