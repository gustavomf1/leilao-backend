package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.config.RequirePermission;
import backstage.project.erpleilao.dtos.LoteDisplayDTO;
import backstage.project.erpleilao.dtos.LoteRequestDTO;
import backstage.project.erpleilao.entity.enums.Acao;
import backstage.project.erpleilao.entity.enums.Ambiente;
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
    @RequirePermission(acao = Acao.CRIAR, ambiente = Ambiente.LOTES)
    public ResponseEntity<LoteDisplayDTO> cadastrar(@RequestBody @Valid LoteRequestDTO dados) {
        return ResponseEntity.ok(service.cadastrar(dados));
    }

    @GetMapping
    @Operation(summary = "Lista todos os lotes", description = "Use ?naoVendido=true para filtrar apenas lotes não vendidos no leilão")
    @RequirePermission(acao = Acao.VISUALIZAR, ambiente = Ambiente.LOTES)
    public ResponseEntity<List<LoteDisplayDTO>> listarTodos(
            @RequestParam(required = false) Boolean naoVendido) {
        return ResponseEntity.ok(service.listarTodos(naoVendido));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um lote por ID", security = {@SecurityRequirement(name = "bearer-key")})
    @RequirePermission(acao = Acao.VISUALIZAR, ambiente = Ambiente.LOTES)
    public ResponseEntity<LoteDisplayDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um lote", security = {@SecurityRequirement(name = "bearer-key")})
    @RequirePermission(acao = Acao.EDITAR, ambiente = Ambiente.LOTES)
    public ResponseEntity<LoteDisplayDTO> atualizar(@PathVariable Long id, @RequestBody @Valid LoteRequestDTO dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @PatchMapping("/{id}/preco")
    @Operation(summary = "Registra o valor do lance e finaliza o lote (AGUARDANDO_LANCE → FINALIZADO)",
               security = {@SecurityRequirement(name = "bearer-key")})
    @RequirePermission(acao = Acao.EDITAR, ambiente = Ambiente.LOTES)
    public ResponseEntity<LoteDisplayDTO> registrarPreco(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, java.math.BigDecimal> body) {
        return ResponseEntity.ok(service.registrarPreco(id, body.get("precoCompra")));
    }

    /**
     * Avança o lote para o próximo status no fluxo:
     * AGUARDANDO_ESCRITORIO → AGUARDANDO_LANCE → (via /preco) → FINALIZADO
     */
    @PatchMapping("/{id}/status/avancar")
    @Operation(summary = "Avança o status do lote", description = "Progressão: AGUARDANDO_ESCRITORIO → AGUARDANDO_LANCE → (PATCH /preco) → FINALIZADO",
               security = {@SecurityRequirement(name = "bearer-key")})
    @RequirePermission(acao = Acao.EDITAR, ambiente = Ambiente.LOTES)
    public ResponseEntity<LoteDisplayDTO> avancarStatus(@PathVariable Long id) {
        return ResponseEntity.ok(service.avancarStatus(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um lote", security = {@SecurityRequirement(name = "bearer-key")})
    @RequirePermission(acao = Acao.DELETAR, ambiente = Ambiente.LOTES)
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
