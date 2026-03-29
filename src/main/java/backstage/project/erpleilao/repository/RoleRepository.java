package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r WHERE r.role_nome = :nome")
    Optional<RoleEntity> findByNome(@Param("nome") String nome);

    @Query("SELECT COUNT(r) > 0 FROM RoleEntity r WHERE r.role_nome = :nome")
    boolean existsByNome(@Param("nome") String nome);
}
