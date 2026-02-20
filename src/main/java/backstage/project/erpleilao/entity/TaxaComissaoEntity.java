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

    @Column(name = "tax_porcentagem", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentagem;

    @Column(name = "tax_tipo_cliente", nullable = false)
    private String tipoCliente;

    @Column(name = "tax_inativo", nullable = false, length = 1)
    private String inativo = "N";
}
