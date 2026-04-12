package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.config.RequirePermission;
import backstage.project.erpleilao.dtos.TaxaRequestDTO;
import backstage.project.erpleilao.dtos.TaxaResponseDTO;
import backstage.project.erpleilao.entity.enums.Acao;
import backstage.project.erpleilao.entity.enums.Ambiente;
import backstage.project.erpleilao.service.TaxaComissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxas-comissao")
@Tag(name = "Taxas de Comissão", description = "Gestão de percentagens de comissão por tipo de cliente")
@CrossOrigin("*")
public class TaxaComissaoController {
    @Autowired
    private TaxaComissaoService service;

    @GetMapping
    @Operation(summary = "Lista todas as taxas ativas")
    @RequirePermission(acao = Acao.VISUALIZAR, ambiente = Ambiente.TAXAS)
    public ResponseEntity<List<TaxaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma taxa por ID")
    @RequirePermission(acao = Acao.VISUALIZAR, ambiente = Ambiente.TAXAS)
    public ResponseEntity<TaxaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova taxa")
    @RequirePermission(acao = Acao.CRIAR, ambiente = Ambiente.TAXAS)
    public ResponseEntity<TaxaResponseDTO> salvar(@RequestBody TaxaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma taxa existente")
    @RequirePermission(acao = Acao.EDITAR, ambiente = Ambiente.TAXAS)
    public ResponseEntity<TaxaResponseDTO> atualizar(@PathVariable Long id, @RequestBody TaxaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativa uma taxa (Exclusão Lógica)")
    @RequirePermission(acao = Acao.DELETAR, ambiente = Ambiente.TAXAS)
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
