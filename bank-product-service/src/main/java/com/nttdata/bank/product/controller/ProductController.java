package com.nttdata.bank.product.controller;

import com.nttdata.bank.product.bankproduct.business.ProductService;
import com.nttdata.bank.product.bankproduct.business.entity.Product;
import com.nttdata.bank.product.bankproduct.business.entity.ProductDto;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @GetMapping
  public Observable<Product> getAll() {
    return productService.getAll();
  }

  @PostMapping
  public Maybe<Product> save(@Valid @RequestBody ProductDto productDto) {
    return productService.save(productDto);
  }

  @PostMapping("/allProducts")
  public Flowable<Product> saveAllProduct(@RequestBody List<ProductDto> productList) {
    return productService.saveAllProduct(productList);
  }

  @PutMapping
  public Maybe<Product> update(@Valid @RequestBody ProductDto productDto) {
    return productService.update(productDto);
  }

  @DeleteMapping("{productId}")
  public Completable deleteBy(@PathVariable("productId") Integer productId) {
    return productService.delete(productId);
  }

}
