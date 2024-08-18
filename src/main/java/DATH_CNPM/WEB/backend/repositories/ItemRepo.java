package DATH_CNPM.WEB.backend.repositories;

import DATH_CNPM.WEB.backend.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {
    List<Item> findByCartId(Long cartId);
    List<Item> findByOrderId(Long orderId);
}
