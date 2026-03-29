package backstage.project.erpleilao.entity;

import backstage.project.erpleilao.entity.enums.TipoChavePix;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pix")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pix_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoChavePix pix_tipo;

    @Column(nullable = false)
    private String pix_chave;

    @ManyToOne
    @JoinColumn(name = "pix_usuario_id", nullable = false)
    private UsuarioEntity pix_usuario;
}
