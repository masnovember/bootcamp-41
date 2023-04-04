package com.nttdata.bank.product.bankproduct.business.impl;

import com.nttdata.bank.product.bankproduct.business.ProductService;
import com.nttdata.bank.product.bankproduct.business.entity.Product;
import com.nttdata.bank.product.bankproduct.business.entity.ProductDto;
import com.nttdata.bank.product.bankproduct.business.entity.TransactionAccountDto;
import com.nttdata.bank.product.bankproduct.business.repository.ProductRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <br/> Clase que implementa ProductService<br/>
 * Copyright: .
 */

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private Mapper mapper;

  @Override
  public Observable<Product> getAll() {
    return productRepository
          .findAll()
          .toObservable();
  }

  @Override
  public Maybe<Product> save(ProductDto productDto) {
    return productRepository
        .existsById(productDto.getProductId())
        .flatMapMaybe(isExist -> {
          if (!isExist) {
            return productRepository
                     .save(mapper.map(productDto, Product.class))
                     .cast(Product.class)
                     .toMaybe();
          }
          return Maybe.empty();
        });
  }

  @Override
   public Flowable<Product> saveAllProduct(List<ProductDto> productDtoList) {
    List<Product> productList = productDtoList
                                .stream()
                                .map(productDto -> mapper.map(productDto, Product.class))
                                .collect(Collectors.toList());
    return productRepository.saveAll(productList);
  }

  @Override
  public Maybe<Product> update(ProductDto productDto) {
    return productRepository
        .existsById(productDto.getProductId())
        .flatMapMaybe(isExist -> {
          if (isExist) {
            return productRepository
                .save(mapper.map(productDto, Product.class))
                .cast(Product.class)
                .toMaybe();
          }
          return Maybe.empty();
        });
  }

  @Override
  public Completable delete(Integer productId) {
    return productRepository.deleteById(productId);
  }

  @Override
  public Maybe<Product> findById(Integer productId) {
    return productRepository.
        findById(productId);
  }

  @Override
  public Observable<Object> getCommissionsByProduct(Integer getMonth, Integer getYear) {
    return Observable.empty();
  }


}
