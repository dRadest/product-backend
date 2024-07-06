package com.dradest.backend.product.jpa.repositories;

import com.dradest.backend.product.jpa.model.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class ProductJpaRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductJpaRepositoryTest.class);

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void testFindByName() {
        LOGGER.debug("----- testFindByName -----");

        String productName = "Shirt";

        Optional<Product> optionalProduct = productJpaRepository.findByName(productName);
        assertTrue(optionalProduct.isPresent());
        Product product = optionalProduct.get();
        assertNotNull(product);
        assertNotNull(product.getName());
        assertEquals(productName, product.getName());
    }

    @Test
    public void testDeleteByName() {
        Product product = new Product();
        String productName = "Test product name";
        product.setName(productName);
        product.setDescription("Test product description");
        product.setPrice(BigDecimal.TEN);

        productJpaRepository.save(product);

        assertTrue(productJpaRepository.findByName(productName).isPresent());

        int deletedProducts = productJpaRepository.deleteByName(productName);
        assertEquals(1, deletedProducts);

        assertFalse(productJpaRepository.findByName(productName).isPresent());
    }
}
