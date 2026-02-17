package backstage.project.erpleilao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fazendas")
@Getter @Setter
public class Fazenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faz_id;

    private String faz_nome;
    private String faz_inscricao;
    private String faz_cnpj;
    private String faz_uf;
    private String faz_cidade;

    @Column(name = "faz_inativo", nullable = false, length = 1)
    private String faz_inativo = "N";

    @ManyToOne
    @JoinColumn(name = "faz_titular", nullable = false)
    private UsuarioEntity faz_titular;
}
