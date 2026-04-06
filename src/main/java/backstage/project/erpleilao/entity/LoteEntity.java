package backstage.project.erpleilao.entity;

import backstage.project.erpleilao.entity.enums.StatusLote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "lotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private Integer qntdAnimais;
    private String sexo;
    private Integer idadeEmMeses;
    private Double peso;
    private String raca;
    private String especie;
    private String categoriaAnimal;
    private String obs;

    // Nullable — preenchido pelo responsável de preço (não pelo manejo)
    private BigDecimal precoCompra;

    // Nome informal do vendedor digitado pelo manejo (ex: "Zezin")
    private String vendedorNomeRascunho;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLote status = StatusLote.AGUARDANDO_ESCRITORIO;

    @ManyToOne
    @JoinColumn(name = "leilao_id")
    private LeilaoEntity leilao;

    // Nullable — vinculado pelo escritório após confirmar o nome
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private UsuarioEntity vendedor;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private UsuarioEntity comprador;
}
