package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.EspecieRequestDTO;
import backstage.project.erpleilao.dtos.EspecieResponseDTO;
import backstage.project.erpleilao.service.EspecieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especies")
@Tag(name = "Espécies", description = "Gestão de espécies animais")
@CrossOrigin("*")
public class EspecieController {

    @Autowired
    private EspecieService service;

    @GetMapping
    @Operation(summary = "Lista espécies ativas")
    public ResponseEntity<List<EspecieResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/todas")
    @Operation(summary = "Lista todas as espécies (ativas e inativas)")
    public ResponseEntity<List<EspecieResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca espécie por ID")
    public ResponseEntity<EspecieResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra nova espécie")
    public ResponseEntity<EspecieResponseDTO> salvar(@RequestBody EspecieRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza espécie")
    public ResponseEntity<EspecieResponseDTO> atualizar(@PathVariable Long id, @RequestBody EspecieRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Ativa/Inativa espécie")
    public ResponseEntity<EspecieResponseDTO> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}
