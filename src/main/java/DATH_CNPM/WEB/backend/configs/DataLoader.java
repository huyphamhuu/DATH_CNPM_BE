package DATH_CNPM.WEB.backend.configs;

import DATH_CNPM.WEB.backend.models.*;
import DATH_CNPM.WEB.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepo.count() > 0 || productRepo.count() > 0 || userRepo.count() > 0) {
            return;
        }

        // Create Categories
        Category categoryMenShirts = new Category();
        categoryMenShirts.setName("Men Shirts");
        categoryRepo.save(categoryMenShirts);

        Category categoryMenPants = new Category();
        categoryMenPants.setName("Men Pants");
        categoryRepo.save(categoryMenPants);

        Category categoryWomenShirts = new Category();
        categoryWomenShirts.setName("Women Shirts");
        categoryRepo.save(categoryWomenShirts);

        Category categoryWomenPants = new Category();
        categoryWomenPants.setName("Women Pants");
        categoryRepo.save(categoryWomenPants);

        // Create Products
        List<Product> products = Arrays.asList(
                createProduct("Plaid Shirt", "Men's Spring Plaid Long Sleeve Shirt", 118000, 200, Collections.singletonList("https://example.com/image1.jpg"), Arrays.asList("L", "XL", "XXL", "XXXL"), Collections.singletonList("Black"), categoryMenShirts),
                createProduct("Denim Jacket", "Men's Denim Jacket", 200000, 100, Collections.singletonList("https://example.com/image2.jpg"), Arrays.asList("M", "L", "XL"), Collections.singletonList("Blue"), categoryMenShirts),
                createProduct("Leather Jacket", "Men's Leather Jacket", 500000, 50, Collections.singletonList("https://example.com/image3.jpg"), Arrays.asList("L", "XL"), Collections.singletonList("Black"), categoryMenShirts),
                createProduct("T-shirt", "Men's Casual T-shirt", 80000, 300, Collections.singletonList("https://example.com/image4.jpg"), Arrays.asList("M", "L", "XL"), Arrays.asList("White", "Black"), categoryMenShirts),
                createProduct("Sweater", "Men's Winter Sweater", 150000, 150, Collections.singletonList("https://example.com/image5.jpg"), Arrays.asList("L", "XL", "XXL"), Collections.singletonList("Grey"), categoryMenShirts),
                createProduct("Blouse", "Women's Summer Blouse", 90000, 200, Collections.singletonList("https://example.com/image6.jpg"), Arrays.asList("S", "M", "L"), Collections.singletonList("White"), categoryWomenShirts),
                createProduct("Dress", "Women's Evening Dress", 250000, 80, Collections.singletonList("https://example.com/image7.jpg"), Arrays.asList("M", "L"), Collections.singletonList("Red"), categoryWomenShirts),
                createProduct("Skirt", "Women's Casual Skirt", 120000, 120, Collections.singletonList("https://example.com/image8.jpg"), Arrays.asList("S", "M", "L"), Collections.singletonList("Black"), categoryWomenPants),
                createProduct("Blazer", "Women's Formal Blazer", 300000, 70, Collections.singletonList("https://example.com/image9.jpg"), Arrays.asList("M", "L", "XL"), Collections.singletonList("Navy"), categoryWomenShirts),
                createProduct("Cardigan", "Women's Cardigan", 180000, 100, Collections.singletonList("https://example.com/image10.jpg"), Arrays.asList("L", "XL"), Collections.singletonList("Beige"), categoryWomenShirts)
        );
        productRepo.saveAll(products);

        // Create Users
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setEmail("user1@example.com");
        userRepo.save(user1);

        // Create Carts
        Cart cart1 = new Cart();
        cart1.setUser(user1);
        cartRepo.save(cart1);

        // Create Items
        Item item1 = new Item();
        item1.setQuantity(1);
        item1.setPrice(products.get(0).getPrice());
        item1.setProduct(products.get(0));
        item1.setCart(cart1);
        itemRepo.save(item1);

        // Create Orders
        Order order1 = new Order();
        order1.setOrderDate(LocalDateTime.now());
        order1.setTotalPrice(item1.getPrice());
        order1.setUser(user1);
        order1.setItems(Collections.singletonList(item1));
        orderRepo.save(order1);

        // Associate Items with Order
        item1.setOrder(order1);
        itemRepo.save(item1);
    }

    private Product createProduct(String name, String description, double price, int availableQuantity, List<String> imageUrl, List<String> sizes, List<String> colors, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setAvailableQuantity(availableQuantity);
        product.setImageUrl(imageUrl);
        product.setSizes(sizes);
        product.setColors(colors);
        product.setCategory(category);
        return product;
    }
}
