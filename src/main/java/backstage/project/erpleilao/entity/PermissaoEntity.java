package backstage.project.erpleilao.entity;

import backstage.project.erpleilao.entity.enums.Acao;
import backstage.project.erpleilao.entity.enums.Ambiente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_permissoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role_id", "acao", "ambiente"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Acao acao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ambiente ambiente;
}
