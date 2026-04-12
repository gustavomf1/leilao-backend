package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.LoteEntity;
import backstage.project.erpleilao.entity.enums.StatusLote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<LoteEntity, Long> {
    List<LoteEntity> findByLeilaoId(Long leilaoId);
    List<LoteEntity> findByNaoVendidoNoLeilao(String naoVendidoNoLeilao);
    List<LoteEntity> findByLeilaoIdAndStatusIn(Long leilaoId, List<StatusLote> statuses);
}
