package DATH_CNPM.WEB.backend.controllers;

import DATH_CNPM.WEB.backend.models.Cart;
import DATH_CNPM.WEB.backend.models.Item;
import DATH_CNPM.WEB.backend.models.Order;
import DATH_CNPM.WEB.backend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Optional<Cart> getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping
    public Cart createCart(@RequestBody(required = false) Cart cart) {
        if (cart == null) {
            cart = new Cart();
        }
        return cartService.saveCart(cart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        cart.setId(id);
        return cartService.saveCart(cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }

    @PostMapping("/add/{cartId}/items")
    public Item addItemToCart(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {

        return cartService.addItemToCart(cartId, productId, quantity);
    }

    @DeleteMapping("/items/{itemId}")
    public void removeItemFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
    }

    @GetMapping("/{cartId}/items")
    public List<Item> getItemsByCartId(@PathVariable Long cartId) {
        return cartService.getItemsByCartId(cartId);
    }

    @PostMapping("/{cartId}/purchase")
    public Order purchaseCart(@PathVariable Long cartId) {
        return cartService.purchaseCart(cartId);
    }

    @PutMapping("/{cartId}/items/{itemId}/increase")
    public Item increaseItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId) {
        return cartService.increaseItemQuantity(cartId, itemId);
    }

    // Mapping để giảm số lượng item đi 1
    @PutMapping("/{cartId}/items/{itemId}/decrease")
    public Item decreaseItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId) {
        return cartService.decreaseItemQuantity(cartId, itemId);
    }

}
