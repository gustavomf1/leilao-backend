package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.CondicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CondicaoRepository extends JpaRepository<CondicaoEntity, Long> {
    @Query("SELECT c FROM CondicaoEntity c WHERE c.inativo = 'N'")
    List<CondicaoEntity> findAllAtivas();
}
