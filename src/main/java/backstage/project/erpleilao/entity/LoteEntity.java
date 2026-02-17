package backstage.project.erpleilao.entity;

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
    private BigDecimal precoCompra;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private UsuarioEntity vendedor;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private UsuarioEntity comprador;
}
