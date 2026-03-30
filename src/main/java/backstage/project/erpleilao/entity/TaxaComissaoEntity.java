package backstage.project.erpleilao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "taxas_comissao")
@Getter
@Setter
public class TaxaComissaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private Long id;

    @Column(name = "tax_comissao_vendedor", nullable = false, precision = 5, scale = 2)
    private BigDecimal comissaoVendedor;

    @Column(name = "tax_comissao_comprador", nullable = false, precision = 5, scale = 2)
    private BigDecimal comissaoComprador;

    @Column(name = "tax_especie", nullable = false, length = 20)
    private String especie;

    @Column(name = "tax_tipo_leilao", nullable = false, length = 20)
    private String tipoLeilao;

    @Column(name = "tax_inativo", nullable = false, length = 1)
    private String inativo = "N";
}
