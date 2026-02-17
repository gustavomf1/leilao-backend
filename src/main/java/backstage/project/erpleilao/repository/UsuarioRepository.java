package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.UsuarioEntity;
import backstage.project.erpleilao.entity.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    @Query("SELECT COUNT(u) > 0 FROM UsuarioEntity u WHERE u.usu_email = :email")
    boolean existsByUsu_email(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM UsuarioEntity u WHERE u.usu_cpf = :cpf")
    boolean existsByUsu_cpf(@Param("cpf") String cpf);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.usu_tipo = :tipo AND u.usu_inativo = 'N'")
    List<UsuarioEntity> findByTipo(@Param("tipo") TipoUsuario tipo);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.usu_email = :email")
    UserDetails findByUsuEmail(@Param("email") String email);
}
