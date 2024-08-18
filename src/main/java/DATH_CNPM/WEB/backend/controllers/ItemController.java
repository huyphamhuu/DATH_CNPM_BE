package DATH_CNPM.WEB.backend.controllers;

import DATH_CNPM.WEB.backend.models.Item;
import DATH_CNPM.WEB.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/cart/{cartId}")
    public List<Item> getItemsByCartId(@PathVariable Long cartId) {
        return itemService.getItemsByCartId(cartId);
    }

    @GetMapping("/order/{orderId}")
    public List<Item> getItemsByOrderId(@PathVariable Long orderId) {
        return itemService.getItemsByOrderId(orderId);
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        return itemService.saveItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
