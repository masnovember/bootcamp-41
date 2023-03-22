package com.nttdata.bank.product.controller;


import com.nttdata.api.ProductsApiDelegate;
import com.nttdata.bank.product.bankproduct.business.ProductService;
import io.reactivex.rxjava3.core.Completable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class ProductsApiImpl implements ProductsApiDelegate {

  private final ProductService productService;
  @Override
  public ResponseEntity<Void> deleteBy(Integer productId) {
    log.info("OpenApi no generó los metodos reactivos, falta investigar");
          productService.delete(productId);
          return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
  }

}
