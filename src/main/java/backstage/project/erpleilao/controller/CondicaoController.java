package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.CondicaoRequestDTO;
import backstage.project.erpleilao.dtos.CondicaoResponseDTO;
import backstage.project.erpleilao.service.CondicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/condicoes")
@Tag(name = "Condições", description = "Gerenciamento de condições de leilão")
@CrossOrigin("*")
public class CondicaoController {
    @Autowired
    private CondicaoService service;

    @GetMapping
    @Operation(summary = "Lista todas as condições ativas")
    public ResponseEntity<List<CondicaoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma condição por ID")
    public ResponseEntity<CondicaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova condição")
    public ResponseEntity<CondicaoResponseDTO> salvar(@RequestBody CondicaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma condição existente")
    public ResponseEntity<CondicaoResponseDTO> atualizar(@PathVariable Long id, @RequestBody CondicaoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativa uma condição (Exclusão Lógica)")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
