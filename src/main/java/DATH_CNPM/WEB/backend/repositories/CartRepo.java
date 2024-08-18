package DATH_CNPM.WEB.backend.repositories;

import DATH_CNPM.WEB.backend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}
