package backstage.project.erpleilao.controller;

import backstage.project.erpleilao.config.RequirePermission;
import backstage.project.erpleilao.dtos.LeilaoDTO;
import backstage.project.erpleilao.dtos.LeilaoResumoDTO;
import backstage.project.erpleilao.entity.LeilaoEntity;
import backstage.project.erpleilao.entity.enums.Acao;
import backstage.project.erpleilao.entity.enums.Ambiente;
import backstage.project.erpleilao.service.LeilaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leiloes")
@Tag(name = "Leilão", description = "Endpoints para gerenciamento de leilões")
public class LeilaoController {

    @Autowired
    private LeilaoService leilaoService;

    @GetMapping
    @Operation(
            summary = "Listar todos os leilões",
            description = "Retorna uma lista com todos os leilões cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeilaoEntity.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    @RequirePermission(acao = Acao.VISUALIZAR, ambiente = Ambiente.LEILOES)
    public ResponseEntity<List<LeilaoEntity>> listarTodos() {
        return ResponseEntity.ok(leilaoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar leilão por ID",
            description = "Retorna os dados de um leilão específico com base no ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Leilão encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeilaoEntity.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Leilão não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    @RequirePermission(acao = Acao.VISUALIZAR, ambiente = Ambiente.LEILOES)
    public ResponseEntity<LeilaoEntity> buscarPorId(
            @Parameter(description = "ID do leilão a ser buscado", required = true, example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(leilaoService.buscarPorId(id));
    }

    @GetMapping("/{id}/resumo")
    @Operation(summary = "Buscar resumo do leilão", description = "Retorna detalhes do leilão com lotes vendidos, restantes e faturamento")
    @RequirePermission(acao = Acao.VISUALIZAR, ambiente = Ambiente.LEILOES)
    public ResponseEntity<LeilaoResumoDTO> buscarResumo(@PathVariable Long id) {
        return ResponseEntity.ok(leilaoService.buscarResumo(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar novo leilão",
            description = "Cadastra um novo leilão no sistema com base nos dados informados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Leilão criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeilaoEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    @RequirePermission(acao = Acao.CRIAR, ambiente = Ambiente.LEILOES)
    public ResponseEntity<LeilaoEntity> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do leilão a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LeilaoDTO.class))
            )
            @RequestBody LeilaoDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leilaoService.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar leilão",
            description = "Atualiza os dados de um leilão existente com base no ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Leilão atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeilaoEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Leilão não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    @RequirePermission(acao = Acao.EDITAR, ambiente = Ambiente.LEILOES)
    public ResponseEntity<LeilaoEntity> atualizar(
            @Parameter(description = "ID do leilão a ser atualizado", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do leilão",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LeilaoDTO.class))
            )
            @RequestBody LeilaoDTO dto
    ) {
        return ResponseEntity.ok(leilaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar leilão",
            description = "Remove um leilão do sistema com base no ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Leilão removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Leilão não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    @RequirePermission(acao = Acao.DELETAR, ambiente = Ambiente.LEILOES)
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do leilão a ser removido", required = true, example = "1")
            @PathVariable Long id
    ) {
        leilaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}