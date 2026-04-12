package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.EspecieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EspecieRepository extends JpaRepository<EspecieEntity, Long> {

    @Query("SELECT e FROM EspecieEntity e WHERE e.inativo = 'N' ORDER BY e.nome")
    List<EspecieEntity> findAllAtivas();
}
