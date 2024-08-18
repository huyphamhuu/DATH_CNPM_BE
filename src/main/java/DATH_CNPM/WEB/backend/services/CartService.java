package DATH_CNPM.WEB.backend.services;

import DATH_CNPM.WEB.backend.models.Cart;
import DATH_CNPM.WEB.backend.models.Item;
import DATH_CNPM.WEB.backend.models.Order;
import DATH_CNPM.WEB.backend.models.Product;
import DATH_CNPM.WEB.backend.repositories.CartRepo;
import DATH_CNPM.WEB.backend.repositories.ItemRepo;
import DATH_CNPM.WEB.backend.repositories.OrderRepo;
import DATH_CNPM.WEB.backend.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    public List<Cart> getAllCarts() {
        return cartRepo.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepo.findById(id);
    }

    public Cart saveCart(Cart cart) {
        return cartRepo.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepo.deleteById(id);
    }

    public Item addItemToCart(Long cartId, Long productId, int quantity) {
        Optional<Cart> optionalCart = cartRepo.findById(cartId);
        Optional<Product> optionalProduct = productRepo.findById(productId);

        if (optionalCart.isPresent() && optionalProduct.isPresent()) {
            Cart cart = optionalCart.get();
            Product product = optionalProduct.get();

            Item item = new Item();
            item.setProduct(product);
            item.setCart(cart);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice() * quantity);

            itemRepo.save(item);
            return item;
        } else {
            throw new RuntimeException("Cart or Product not found");
        }
    }

    public void removeItemFromCart(Long itemId) {
        itemRepo.deleteById(itemId);
    }

    public List<Item> getItemsByCartId(Long cartId) {
        return itemRepo.findByCartId(cartId);
    }

    public Order purchaseCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepo.findById(cartId);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Order order = new Order();
            order.setUser(cart.getUser());
            order.setOrderDate(LocalDateTime.now());
            order.setItems(cart.getItems());
            double totalPrice = 0;

            for (Item item : cart.getItems()) {
                Product product = item.getProduct();
                if (product.getAvailableQuantity() < item.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
                product.setAvailableQuantity(product.getAvailableQuantity() - item.getQuantity());
                productRepo.save(product);
                totalPrice += item.getPrice();
                item.setOrder(order);
                itemRepo.save(item);
            }

            order.setTotalPrice(totalPrice);
            orderRepo.save(order);

            // Clear the cart
            cart.getItems().clear();
            cartRepo.save(cart);

            return order;
        } else {
            throw new RuntimeException("Cart not found");
        }
    }
    public Item increaseItemQuantity(Long cartId, Long itemId) {
        Optional<Item> optionalItem = itemRepo.findById(itemId);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (item.getCart().getId().equals(cartId)) {
                item.setQuantity(item.getQuantity() + 1);
                item.setPrice(item.getProduct().getPrice() * item.getQuantity());
                return itemRepo.save(item);
            } else {
                throw new RuntimeException("Item không thuộc về giỏ hàng này.");
            }
        } else {
            throw new RuntimeException("Item không tìm thấy.");
        }
    }

    // Phương thức giảm số lượng item đi 1
    public Item decreaseItemQuantity(Long cartId, Long itemId) {
        Optional<Item> optionalItem = itemRepo.findById(itemId);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (item.getCart().getId().equals(cartId)) {
                if (item.getQuantity() > 1) { // Chỉ giảm nếu số lượng lớn hơn 1
                    item.setQuantity(item.getQuantity() - 1);
                    item.setPrice(item.getProduct().getPrice() * item.getQuantity());
                    return itemRepo.save(item);
                } else {
                    throw new RuntimeException("Số lượng không thể nhỏ hơn 1.");
                }
            } else {
                throw new RuntimeException("Item không thuộc về giỏ hàng này.");
            }
        } else {
            throw new RuntimeException("Item không tìm thấy.");
        }
    }
}
