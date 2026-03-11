package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.LeilaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeilaoRepository extends JpaRepository<LeilaoEntity, Long> {
}
