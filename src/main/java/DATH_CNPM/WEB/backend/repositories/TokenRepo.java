package DATH_CNPM.WEB.backend.repositories;
import DATH_CNPM.WEB.backend.models.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface  TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

}
