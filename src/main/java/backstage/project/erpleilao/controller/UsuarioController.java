package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.dtos.UsuarioFuncionarioDTO;
import backstage.project.erpleilao.dtos.UsuarioFuncionarioResponseDTO;
import backstage.project.erpleilao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
