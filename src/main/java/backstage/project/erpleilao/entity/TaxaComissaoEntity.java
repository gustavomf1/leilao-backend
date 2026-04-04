package backstage.project.erpleilao.entity;

import backstage.project.erpleilao.entity.enums.TipoLeilao;
import backstage.project.erpleilao.entity.enums.TaxaPor;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tax_especie_id", nullable = false)
    private EspecieEntity especie;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_tipo_leilao", nullable = false, length = 20)
    private TipoLeilao tipoLeilao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_taxa_por", nullable = false, length = 10)
    private TaxaPor taxaPor = TaxaPor.ANIMAL;

    @Column(name = "tax_inativo", nullable = false, length = 1)
    private String inativo = "N";
}
