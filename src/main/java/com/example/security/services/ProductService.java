package com.example.security.services;

import com.example.security.dto.ProductRequest;
import com.example.security.dto.ProductResponse;
import com.example.security.models.Product;
import com.example.security.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    final ProductRepository repository;

    final ModelMapper modelMapper;

    public ProductService(ProductRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<ProductResponse> getAll() {
        List<Product> products = repository.findAll();
        List<ProductResponse> responses = products.stream().map(product ->
                modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
        return responses;
    }

    public ProductResponse insert(ProductRequest productRequest) {
        Product productInsert = modelMapper.map(productRequest, Product.class);
        Product productInserted = repository.save(productInsert);
        return modelMapper.map(productInserted, ProductResponse.class);
    }

    public ProductResponse update(ProductRequest productRequest, Long id) {
        Product productUpdate = modelMapper.map(productRequest, Product.class);
        repository.findById(id).map(product -> {
            product.setName(productUpdate.getName());
            product.setPrice(productUpdate.getPrice());
            return repository.save(product);
        }).orElseGet(() -> {
            productUpdate.setId(id);
            return repository.save(productUpdate);
        });
        return modelMapper.map(productUpdate, ProductResponse.class);
    }

    public ProductResponse delete(Long id) {
        boolean exists = repository.existsById(id);
        ProductResponse productResponse = modelMapper.map(repository.findById(id), ProductResponse.class);
        if (exists) {
            repository.deleteById(id);
        }
        return productResponse;
    }

    public ProductResponse findById(Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            Optional<Product> productFound = repository.findById(id);
            return modelMapper.map(productFound, ProductResponse.class);
        }
        return null;
    }
}
