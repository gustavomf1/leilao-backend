package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.AtribuirRolesDTO;
import backstage.project.erpleilao.dtos.RoleRequestDTO;
import backstage.project.erpleilao.dtos.RoleResponseDTO;
import backstage.project.erpleilao.dtos.UsuarioFuncionarioResponseDTO;
import backstage.project.erpleilao.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Gerenciamento de roles e permissões (somente admin)")
public class RoleController {

    @Autowired
    private RoleService service;

    @Operation(summary = "Cria uma nova role")
    @ApiResponse(responseCode = "201", description = "Role criada com sucesso")
    @PostMapping
    public ResponseEntity<RoleResponseDTO> criar(@RequestBody RoleRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @Operation(summary = "Lista todas as roles")
    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Busca role por ID")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualiza uma role")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> atualizar(@PathVariable Long id, @RequestBody RoleRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Deleta uma role")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atribui roles e isAdmin a um funcionário")
    @PutMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<UsuarioFuncionarioResponseDTO> atribuirRoles(
            @PathVariable Long funcionarioId,
            @RequestBody AtribuirRolesDTO dto) {
        return ResponseEntity.ok(service.atribuirRoles(funcionarioId, dto));
    }
}
