package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.TaxaComissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaxaComissaoRepository extends JpaRepository<TaxaComissaoEntity, Long> {
    @Query("SELECT t FROM TaxaComissaoEntity t WHERE t.inativo = 'N'")
    List<TaxaComissaoEntity> findAllAtivas();
}
