package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.FazendaRequestDTO;
import backstage.project.erpleilao.dtos.FazendaResponseDTO;
import backstage.project.erpleilao.service.FazendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fazendas")
@Tag(name = "Fazendas", description = "Endpoints para gerenciamento de propriedades rurais")
@CrossOrigin("*")
public class FazendaController {

    @Autowired
    private FazendaService service;

    @GetMapping
    @Operation(summary = "Lista todas as fazendas ativas", description = "Retorna apenas fazendas onde inativo = 'N'")
    public ResponseEntity<List<FazendaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma fazenda pelo ID", description = "Retorna os detalhes de uma fazenda ativa específica")
    @ApiResponse(responseCode = "200", description = "Fazenda encontrada")
    @ApiResponse(responseCode = "404", description = "Fazenda não encontrada ou inativa")
    public ResponseEntity<FazendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova fazenda")
    @ApiResponse(responseCode = "201", description = "Fazenda criada com sucesso")
    public ResponseEntity<FazendaResponseDTO> salvar(@RequestBody FazendaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma fazenda existente")
    public ResponseEntity<FazendaResponseDTO> atualizar(@PathVariable Long id, @RequestBody FazendaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativa uma fazenda (Exclusão Lógica)", description = "Altera o status da fazenda para 'S' (Inativo)")
    @ApiResponse(responseCode = "204", description = "Fazenda inativada com sucesso")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}