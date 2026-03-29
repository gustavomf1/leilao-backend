package backstage.project.erpleilao.entity;


import backstage.project.erpleilao.entity.enums.AceiteIntegrado;
import backstage.project.erpleilao.entity.enums.TipoCondicao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "condicoes")
@Getter
@Setter
public class CondicaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "con_id")
    private Long id;

    @Column(name = "con_descricao", nullable = false)
    private String descricao;

    @Column(name = "con_captacao")
    private Integer captacao;

    @Column(name = "con_parcelas")
    private Integer parcelas;

    @Column(name = "con_qtd_dias")
    private Integer qtdDias;

    @Column(name = "con_percentual_desconto", precision = 5, scale = 2)
    private BigDecimal percentualDesconto;

    @Column(name = "con_com_entrada", length = 1)
    private String comEntrada = "N";

    @Column(name = "con_mesmo_dia", length = 1)
    private String mesmoDia = "N";

    @Enumerated(EnumType.STRING)
    @Column(name = "con_tipo_condicao", length = 20)
    private TipoCondicao tipoCondicao;

    @Enumerated(EnumType.STRING)
    @Column(name = "con_aceite_integrado", length = 40)
    private AceiteIntegrado aceiteIntegrado = AceiteIntegrado.NORMAL;

    @Column(name = "con_inativo", nullable = false, length = 1)
    private String inativo = "N";
}
