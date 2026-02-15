package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.*;
import backstage.project.erpleilao.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de funcionários e clientes (pecuaristas) do sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Operation(summary = "Cadastra um novo funcionário", description = "Cria um usuário com perfil administrativo no sistema.")
    @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso")
    @PostMapping("/funcionario")
    public ResponseEntity<UsuarioFuncionarioResponseDTO> criarFuncionario(@RequestBody UsuarioFuncionarioDTO dto) {
        UsuarioFuncionarioResponseDTO response = service.salvarFuncionario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Cadastra um novo cliente", description = "Cria um registro para pecuaristas (vendedores ou compradores).")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    @PostMapping("/cliente")
    public ResponseEntity<UsuarioClienteResponseDTO> criarCliente(@RequestBody UsuarioClienteDTO dto) {
        UsuarioClienteResponseDTO response = service.salvarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Lista todos os funcionários", description = "Retorna apenas os usuários ativos com perfil FUNCIONARIO.")
    @GetMapping("/funcionarios")
    public ResponseEntity<List<UsuarioFuncionarioResponseDTO>> listarFuncionarios() {
        return ResponseEntity.ok(service.listarFuncionarios());
    }

    @Operation(summary = "Lista todos os clientes", description = "Retorna a lista de pecuaristas cadastrados e ativos.")
    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioClienteResponseDTO>> listarClientes() {
        return ResponseEntity.ok(service.listarClientes());
    }

    @Operation(summary = "Busca funcionário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não localizado")
    })
    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<UsuarioFuncionarioResponseDTO> buscarFuncionarioPorId(
            @Parameter(description = "ID único do funcionário", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarFuncionarioPorId(id));
    }

    @Operation(summary = "Busca cliente por ID")
    @GetMapping("/clientes/{id}")
    public ResponseEntity<UsuarioClienteResponseDTO> buscarClientePorId(
            @Parameter(description = "ID único do cliente", example = "5") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarClientePorId(id));
    }

    @Operation(summary = "Atualiza dados de um funcionário")
    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<UsuarioFuncionarioResponseDTO> atualizarFuncionario(
            @PathVariable Long id,
            @RequestBody UsuarioFuncionarioUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizarFuncionario(id, dto));
    }

    @Operation(summary = "Atualiza dados de um cliente")
    @PutMapping("/clientes/{id}")
    public ResponseEntity<UsuarioClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @RequestBody UsuarioClienteUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizarCliente(id, dto));
    }

    @Operation(summary = "Inativação lógica de usuário", description = "Altera o status do usuário para inativo sem removê-lo fisicamente do banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "24", description = "Usuário inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("inativar/{id}")
    public ResponseEntity<Void> inativarUsuario(@PathVariable Long id) {
        service.inativarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}