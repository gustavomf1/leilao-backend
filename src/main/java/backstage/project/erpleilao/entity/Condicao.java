package backstage.project.erpleilao.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "condicoes")
@Getter
@Setter
public class Condicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "con_id")
    private Long id;

    @Column(name = "con_tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "con_descricao", nullable = false)
    private String descricao;

    @Column(name = "con_inativo", nullable = false, length = 1)
    private String inativo = "N";
}
