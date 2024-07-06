package com.dradest.backend.product.service;

import com.dradest.backend.product.exceptions.ProductNotFoundException;
import com.dradest.backend.product.exceptions.ProductPersistException;
import com.dradest.backend.product.jpa.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testFetchAllProducts() {
        List<Product> allProducts = productService.fetchAllProducts();
        assertNotNull(allProducts);
        assertFalse(allProducts.isEmpty());
    }

    @Test
    public void testFetchProductByName() {
        Product productByName = productService.fetchProductByName("Shirt");
        assertNotNull(productByName);

        assertThrows(ProductNotFoundException.class, () -> productService.fetchProductByName("Banana"));
        assertThrows(NullPointerException.class, () -> productService.fetchProductByName(null));
    }

    @Test
    public void testPersistProduct() {
        assertThrows(NullPointerException.class, () -> productService.updateProduct(null));
        assertThrows(NullPointerException.class, () -> productService.updateProduct(new Product()));

        Product productOrange = new Product();
        productOrange.setName("Orange");
        productOrange.setPrice(BigDecimal.ONE);
        productOrange.setDescription("Orange product description");
        assertNull(productOrange.getId());

        Product persistedOrangeProduct = productService.persistProduct(productOrange);
        assertNotNull(persistedOrangeProduct);
        assertNotNull(persistedOrangeProduct.getId());

        String existingProductName = "Shirt";
        Product existingProduct = new Product();
        existingProduct.setName(existingProductName);

        assertThrows(ProductPersistException.class, () -> productService.persistProduct(existingProduct));
    }

    @Test
    public void testUpdateProduct() {
        assertThrows(NullPointerException.class, () -> productService.updateProduct(null));
        assertThrows(NullPointerException.class, () -> productService.updateProduct(new Product()));

        String productName = "Toothbrush";
        Product productByName = productService.fetchProductByName(productName);
        assertNotNull(productByName);

        Product updateToothbrush = new Product();
        updateToothbrush.setName(productName);
        updateToothbrush.setPrice(BigDecimal.ONE);
        updateToothbrush.setDescription("Updated toothbrush description");
        assertNull(updateToothbrush.getId());

        updateToothbrush = productService.updateProduct(updateToothbrush);
        assertNotNull(updateToothbrush);
        assertNotNull(updateToothbrush.getId());

        assertNotEquals(productByName.getDescription(), updateToothbrush.getDescription());

        Product updateNewProduct = new Product();
        updateNewProduct.setName("NewProduct");
        updateNewProduct.setPrice(BigDecimal.ONE);
        updateNewProduct.setDescription("New product description");
        assertNull(updateNewProduct.getId());

        updateNewProduct = productService.updateProduct(updateNewProduct);
        assertNotNull(updateNewProduct);
        assertNotNull(updateNewProduct.getId());


    }

    @Test
    public void testDeleteProduct() {
        String productName = "Shoes";
        Product productByName = productService.fetchProductByName(productName);
        assertNotNull(productByName);

        productService.deleteProduct(productName);

        assertThrows(ProductNotFoundException.class, () -> productService.fetchProductByName(productName));
    }
}
