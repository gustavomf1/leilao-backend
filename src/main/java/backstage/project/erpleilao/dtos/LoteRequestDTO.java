package backstage.project.erpleilao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record LoteRequestDTO(
        @Schema(example = "LOTE-001", description = "Código identificador do lote")
        String codigo,

        @Schema(example = "15", description = "Quantidade de animais no lote")
        Integer qntdAnimais,

        @Schema(example = "M", description = "Sexo dos animais (M/F)")
        String sexo,

        @Schema(example = "24", description = "Idade média em meses")
        Integer idadeEmMeses,

        @Schema(example = "450.5", description = "Peso médio dos animais")
        Double peso,

        @Schema(example = "Nelore", description = "Raça predominante")
        String raca,

        @Schema(example = "Bovino", description = "Espécie do animal")
        String especie,

        @Schema(example = "Garrote", description = "Categoria do animal")
        String categoriaAnimal,

        @Schema(example = "Animais vacinados e prontos para engorda", description = "Observações gerais")
        String obs,

        @Schema(example = "Zezin da Fazenda Boa Vista", description = "Nome informal do vendedor (preenchido pelo manejo)")
        String vendedorNomeRascunho,

        @Schema(example = "5500.00", description = "Preço de compra (preenchido pelo responsável de preço, não pelo manejo)")
        BigDecimal precoCompra,

        @Schema(example = "1", description = "ID do leilão (opcional)")
        Long leilaoId,

        @Schema(example = "1", description = "ID do usuário vendedor (vinculado pelo escritório)")
        Long vendedorId,

        @Schema(example = "null", description = "ID do usuário comprador (preenchido ao finalizar o leilão)")
        Long compradorId
) {}
