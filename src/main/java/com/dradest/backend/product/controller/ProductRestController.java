package com.dradest.backend.product.controller;

import com.dradest.backend.product.jpa.model.Product;
import com.dradest.backend.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductRestController {

    private final ProductService productService;
    private final ProductModelAssembler productModelAssembler;

    public ProductRestController(ProductService productService, ProductModelAssembler productModelAssembler) {
        this.productService = productService;
        this.productModelAssembler = productModelAssembler;
    }

    @GetMapping("/products")
    protected CollectionModel<EntityModel<Product>>all() {

        List<EntityModel<Product>> products = productService.fetchAllProducts().stream()
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductRestController.class).all()).withSelfRel());
    }

    @GetMapping("/products/{name}")
    protected EntityModel<Product> one(@PathVariable String name) {
        Product product = productService.fetchProductByName(name);
        return productModelAssembler.toModel(product);
    }

    @PostMapping("/products")
    protected ResponseEntity<?> newProduct(@RequestBody Product newProduct) {
        EntityModel<Product> entityModel = productModelAssembler.toModel(productService.persistProduct(newProduct));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/products")
    protected ResponseEntity<?> updateProduct(@RequestBody Product product) {

        EntityModel<Product> entityModel = productModelAssembler.toModel(productService.updateProduct(product));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/products/{name}")
    protected ResponseEntity<?> deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);

        return ResponseEntity.noContent().build();
    }
}
