package DATH_CNPM.WEB.backend.repositories;

import DATH_CNPM.WEB.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
