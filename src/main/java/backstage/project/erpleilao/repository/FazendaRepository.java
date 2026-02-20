package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.FazendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FazendaRepository extends JpaRepository<FazendaEntity, Long> {
    @Query("SELECT f FROM FazendaEntity f WHERE f.inativo = 'N'")
    List<FazendaEntity> findAllAtivas();
}
