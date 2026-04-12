package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.LoteDisplayDTO;
import backstage.project.erpleilao.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/publico")
@Tag(name = "Público", description = "Endpoints públicos para o evento de leilão")
public class PublicoLoteController {

    @Autowired
    private LoteService loteService;

    @GetMapping("/leilao/{leilaoId}/lotes")
    @Operation(summary = "Lista lotes públicos do leilão", description = "Retorna lotes em AGUARDANDO_LANCE ou FINALIZADO — sem autenticação")
    public ResponseEntity<List<LoteDisplayDTO>> listarLotesEvento(@PathVariable Long leilaoId) {
        return ResponseEntity.ok(loteService.listarPorLeilaoPublico(leilaoId));
    }

    @PatchMapping("/lote/{id}/preco")
    @Operation(summary = "Registra lance público", description = "Registra o valor do lance via link público — sem autenticação")
    public ResponseEntity<LoteDisplayDTO> registrarPreco(
            @PathVariable Long id,
            @RequestBody Map<String, BigDecimal> body) {
        return ResponseEntity.ok(loteService.registrarPreco(id, body.get("precoCompra")));
    }
}
