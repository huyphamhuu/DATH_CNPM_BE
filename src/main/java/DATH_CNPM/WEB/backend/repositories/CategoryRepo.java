package DATH_CNPM.WEB.backend.repositories;

import DATH_CNPM.WEB.backend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
