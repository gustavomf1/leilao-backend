package backstage.project.erpleilao.repository;

import backstage.project.erpleilao.entity.RecuperacaoSenhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecuperacaoSenhaRepository extends JpaRepository<RecuperacaoSenhaEntity, Long> {
    Optional<RecuperacaoSenhaEntity> findByToken(String token);
}
