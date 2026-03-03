package com.produc.infraestructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produc.application.services.ProductService;
import com.produc.infraestructure.controllers.mappers.ProductDtoMapper;
import com.produc.infraestructure.controllers.models.ProductDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductControllerAdapter {

	private final ProductService service;
	private final ProductDtoMapper mapper;

	@GetMapping
	public List<ProductDto> findAll() {
		return mapper.toProduct(service.findAll());
	}

	@PostMapping
	public ResponseEntity<ProductDto> save(@RequestBody ProductDto productDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(mapper.toProductDto(service.save(mapper.toProduct(productDto))));
	}

	@PutMapping
	public ProductDto update(@RequestBody ProductDto request) {
		return mapper.toProductDto(service.update(mapper.toProduct(request)));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.deleteById(id);
	}

}
