package DATH_CNPM.WEB.backend.services;

import DATH_CNPM.WEB.backend.models.Item;
import DATH_CNPM.WEB.backend.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepo.findById(id);
    }

    public List<Item> getItemsByCartId(Long cartId) {
        return itemRepo.findByCartId(cartId);
    }

    public List<Item> getItemsByOrderId(Long orderId) {
        return itemRepo.findByOrderId(orderId);
    }

    public Item saveItem(Item item) {
        return itemRepo.save(item);
    }

    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }
}
