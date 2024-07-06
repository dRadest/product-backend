package com.dradest.backend.product.jpa.repositories;

import com.dradest.backend.product.jpa.model.Product;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    public void clearData() {
        productJpaRepository.deleteAll();
    }

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

    @Test
    public void testDeleteByName() {
        int noInserts = 5;
        for (int insert = 0; insert < noInserts; insert++) {
            Product product = new Product();
            String name = "Test product " + insert;
            product.setName(name);
            product.setDescription(name + " desc");
            product.setPrice(BigDecimal.TEN);

            productJpaRepository.save(product);
        }

        long count = productJpaRepository.count();
        assertEquals(noInserts, count);

        String deleteByName = "Test product 3";
        int deletedProducts = productJpaRepository.deleteByName(deleteByName);
        assertEquals(1, deletedProducts);

        count = productJpaRepository.count();
        assertEquals(noInserts - 1, count);
    }
}
