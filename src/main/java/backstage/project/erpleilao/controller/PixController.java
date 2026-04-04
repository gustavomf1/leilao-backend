package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.PixRequestDTO;
import backstage.project.erpleilao.dtos.PixResponseDTO;
import backstage.project.erpleilao.entity.enums.TipoChavePix;
import backstage.project.erpleilao.service.PixService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pix")
@Tag(name = "Pix", description = "Endpoints para gerenciamento de chaves Pix")
@CrossOrigin("*")
public class PixController {

    @Autowired
    private PixService service;

    @PostMapping
    @Operation(summary = "Cadastra uma nova chave Pix para um usuário")
    @ApiResponse(responseCode = "201", description = "Chave Pix cadastrada com sucesso")
    public ResponseEntity<PixResponseDTO> cadastrar(@RequestBody @Valid PixRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista todas as chaves Pix de um usuário")
    public ResponseEntity<List<PixResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/tipo/{tipo}")
    @Operation(summary = "Lista as chaves Pix de um usuário filtradas por tipo")
    public ResponseEntity<List<PixResponseDTO>> listarPorUsuarioETipo(
            @PathVariable Long usuarioId,
            @PathVariable TipoChavePix tipo) {
        return ResponseEntity.ok(service.listarPorUsuarioETipo(usuarioId, tipo));
    }

    @DeleteMapping("/{pixId}")
    @Operation(summary = "Remove uma chave Pix")
    @ApiResponse(responseCode = "204", description = "Chave Pix removida com sucesso")
    public ResponseEntity<Void> excluir(@PathVariable Long pixId) {
        service.excluir(pixId);
        return ResponseEntity.noContent().build();
    }
}
