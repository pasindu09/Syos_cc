package test;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import main.pos.ProductInputHandler;
import main.pos.cart.CartService;
import main.product.Product;
import main.product.ProductRepository;
import java.util.*;

public class ProductInputHandlerTest {
    private Scanner scanner;
    private ProductRepository productRepository;
    private CartService cartService;
    private ProductInputHandler productInputHandler;
    private Product product;

    @Before
    public void setUp() {
        scanner = mock(Scanner.class);
        productRepository = mock(ProductRepository.class);
        cartService = mock(CartService.class);

        List<Product> products = new ArrayList<>();
        product = new Product(1, "Test Product", 10.00);
        products.add(product);
        when(productRepository.getAllProducts()).thenReturn(products);

        productInputHandler = new ProductInputHandler(scanner, productRepository, cartService);
    }

    @Test
    public void testHandleProductInputEndsWhenPaynowIsEntered() {
        when(scanner.nextLine()).thenReturn("paynow");
        productInputHandler.handleProductInput();
        verify(scanner, times(1)).nextLine(); // Ensures the loop exits after "paynow"
    }

   

    @Test
    public void testValidProductCodeAddsProductToCart() {
        // Arrange
        when(scanner.nextLine())
                .thenReturn("1")
                .thenReturn("")
                .thenReturn("2")
                .thenReturn("paynow");

        when(scanner.hasNextInt()).thenReturn(true);
        when(scanner.nextInt()).thenReturn(2);

        // Act
        productInputHandler.handleProductInput();

        // Assert
        verify(cartService).addProduct(product, 2);
    }

}
