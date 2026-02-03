package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.usu_email = :email")
    boolean existsByUsu_email(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.usu_cpf = :cpf")
    boolean existsByUsu_cpf(@Param("cpf") String cpf);
}
