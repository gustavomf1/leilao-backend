package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.*;
import backstage.project.erpleilao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping("/funcionario")
    public ResponseEntity<UsuarioFuncionarioResponseDTO> criarFuncionario(@RequestBody UsuarioFuncionarioDTO dto) {
        UsuarioFuncionarioResponseDTO response = service.salvarFuncionario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/cliente")
    public ResponseEntity<UsuarioClienteResponseDTO> criarCliente(@RequestBody UsuarioClienteDTO dto) {
        UsuarioClienteResponseDTO response = service.salvarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<UsuarioFuncionarioResponseDTO>> listarFuncionarios() {
        return ResponseEntity.ok(service.listarFuncionarios());
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioClienteResponseDTO>> listarClientes() {
        return ResponseEntity.ok(service.listarClientes());
    }

    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<UsuarioFuncionarioResponseDTO> buscarFuncionarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarFuncionarioPorId(id));
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<UsuarioClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarClientePorId(id));
    }

    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<UsuarioFuncionarioResponseDTO> atualizarFuncionario(
            @PathVariable Long id,
            @RequestBody UsuarioFuncionarioUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizarFuncionario(id, dto));
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<UsuarioClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @RequestBody UsuarioClienteUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizarCliente(id, dto));
    }

    @DeleteMapping("inativar/{id}")
    public ResponseEntity<Void> inativarUsuario(@PathVariable Long id) {
        service.inativarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
