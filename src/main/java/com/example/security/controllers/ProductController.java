package com.example.security.controllers;

import com.example.security.dto.ProductRequest;
import com.example.security.dto.ProductResponse;
import com.example.security.models.ResponseObject;
import com.example.security.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/product")
public class ProductController {

    final
    ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {
        List<ProductResponse> responses = service.getAll();
        if (responses.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Success", responses)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("404", "Product is empty", "")
            );
        }
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insert(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Inserted", service.insert(productRequest))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@RequestBody ProductRequest productRequest, @PathVariable Long id) {
        ProductResponse productExists = service.findById(id);
        if (productExists != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Updated", service.update(productRequest, id))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("404", "Product not found!", "")
            );
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ProductResponse productExists = service.findById(id);
        if (productExists != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Deleted", service.delete(id))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("404", "Product not found!", "")
            );
        }
    }
}
