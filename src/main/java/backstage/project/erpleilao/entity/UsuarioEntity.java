package backstage.project.erpleilao.entity;

import backstage.project.erpleilao.entity.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usu_id;

    @Column(nullable = false)
    private String usu_nome;

    @Column(unique = true, nullable = false)
    private String usu_email;

    @Column(nullable = true)
    private String usu_senha;

    @Column(unique = true, nullable = false)
    private String usu_cpf;

    @Enumerated(EnumType.STRING)
    private TipoUsuario usu_tipo;

    @Column(name = "usu_inativo", nullable = false, length = 1)
    private String usu_inativo = "N";

    private String usu_telefone;
    private String usu_cidade;
    private String usu_uf;
    private String usu_rg;

    @OneToMany(mappedBy = "faz_titular", cascade = CascadeType.ALL)
    private List<FazendaEntity> usu_fazendaEntities;

    @Column(name = "usu_dt_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime usu_dt_criacao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.usu_tipo.name()));
    }

    @Override
    public String getPassword() {
        return this.usu_senha;
    }

    @Override
    public String getUsername() {
        return this.usu_email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "N".equalsIgnoreCase(this.usu_inativo);
    }
}
