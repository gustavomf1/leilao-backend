package backstage.project.erpleilao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "leilao")
@Getter
@Setter
public class LeilaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lei_id")
    private Long id;

    @Column(name = "lei_local", nullable = false, length = 100)
    private String local;

    @Column(name = "lei_uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "lei_cidade", nullable = false, length = 100)
    private String cidade;

    @Column(name = "lei_descricao", nullable = false)
    private String descricao;

    @Column(name = "lei_data", nullable = false)
    private LocalDate data;

    @Column(name = "lei_inativo", nullable = false, length = 1)
    private String inativo = "N";

    @ManyToOne
    @JoinColumn(name = "lei_con_id", nullable = false)
    private CondicaoEntity condicao;

    @ManyToOne
    @JoinColumn(name = "lei_tax_id", nullable = false)
    private TaxaComissaoEntity taxa;
}