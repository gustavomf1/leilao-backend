package backstage.project.erpleilao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "fazendas")
@Getter @Setter
public class Fazenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faz_id")
    private Long id;

    @Column(name = "faz_nome", nullable = false)
    private String nome;

    @Column(name = "faz_inscricao")
    private String inscricao;

    @Column(name = "faz_cnpj")
    private String cnpj;

    @Column(name = "faz_uf", length = 2)
    private String uf;

    @Column(name = "faz_cidade")
    private String cidade;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "faz_dt_criacao", updatable = false)
    private Date dt_criacao;

    @Column(name = "faz_inativo", nullable = false, length = 1)
    private String inativo = "N";

    @ManyToOne
    @JoinColumn(name = "titular_id", nullable = false) // Mantido titular_id conforme seu script
    private UsuarioEntity faz_titular;

    @PrePersist
    protected void onCreate() {
        this.dt_criacao = new Date();
        if (this.inativo == null) this.inativo = "N";
    }
}
