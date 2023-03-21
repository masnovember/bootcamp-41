package com.nttdata.bank.product.bankproduct.business;

import com.nttdata.bank.product.bankproduct.business.entity.Product;
import com.nttdata.bank.product.bankproduct.business.entity.ProductDto;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public interface ProductService {
  Observable<Product> getAll();

  Maybe<Product> save(ProductDto productDto);

  Flowable<Product> saveAllProduct(List<ProductDto> productList);

  Maybe<Product> update(ProductDto productDto);

  Completable delete(Integer productId);

}
