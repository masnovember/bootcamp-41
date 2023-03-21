package com.nttdata.bank.product.bankproduct.business.repository;

import com.nttdata.bank.product.bankproduct.business.entity.Product;
import org.springframework.data.repository.reactive.RxJava3CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends RxJava3CrudRepository<Product, Integer> {

}
