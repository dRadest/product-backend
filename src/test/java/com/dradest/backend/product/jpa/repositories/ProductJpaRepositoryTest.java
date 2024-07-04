package com.dradest.backend.product.jpa.repositories;

import com.dradest.backend.product.jpa.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductJpaRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductJpaRepositoryTest.class);

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void testFindByName() {
        LOGGER.debug("----- testFindByName -----");

        String name = "test_product";

        Product product = new Product();
        product.setDescription("Test product");
        product.setName(name);
        product.setPrice(BigDecimal.TEN);

        productJpaRepository.save(product);

        Optional<Product> optionalProduct = productJpaRepository.findByName(name);
        assertTrue(optionalProduct.isPresent());
        product = optionalProduct.get();
        assertNotNull(product);
        assertNotNull(product.getName());
        assertEquals(name, product.getName());
    }
}
