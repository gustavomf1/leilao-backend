package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.Condicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CondicaoRepository extends JpaRepository<Condicao, Long> {
    @Query("SELECT c FROM Condicao c WHERE c.inativo = 'N'")
    List<Condicao> findAllAtivas();
}
