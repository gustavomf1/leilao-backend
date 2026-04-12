package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.PixEntity;
import backstage.project.erpleilao.entity.enums.TipoChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PixRepository extends JpaRepository<PixEntity, Long> {

    @Query("SELECT p FROM PixEntity p WHERE p.pix_usuario.usu_id = :usuarioId")
    List<PixEntity> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT p FROM PixEntity p WHERE p.pix_usuario.usu_id = :usuarioId AND p.pix_tipo = :tipo")
    List<PixEntity> findByUsuarioIdAndTipo(@Param("usuarioId") Long usuarioId, @Param("tipo") TipoChavePix tipo);
}
