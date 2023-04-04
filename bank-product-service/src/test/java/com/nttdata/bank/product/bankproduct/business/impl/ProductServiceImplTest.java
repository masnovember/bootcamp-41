package com.nttdata.bank.product.bankproduct.business.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bank.product.bankproduct.business.entity.Product;
import com.nttdata.bank.product.bankproduct.business.entity.ProductDto;
import com.nttdata.bank.product.bankproduct.business.repository.ProductRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import org.dozer.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Autowired
  private Mapper mapper;
  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl productServiceImpl;
  private Product product;
  private ProductDto productDto;

  @BeforeEach
  void setUp() throws IOException {
    product = fromFileFactory(PRODUCT_TEST, Product.class);
    productDto = fromFileFactory(PRODUCT_TEST,ProductDto.class);
  }

  @Test
  @DisplayName("Create list of products Success When exists products")
  public void CreateListOfProductsSuccessWhenWxistsProducts() {
    when(productRepository.findAll()).thenReturn(Flowable.just(product));

    TestObserver testObserver = productServiceImpl.getAll().test();

    assertThat(testObserver).isNotNull();
    testObserver.assertComplete();
    testObserver.assertNoErrors();
  }

  @Test
  @DisplayName("Save product success when no exists")
  public void saveProductSuccessWhenNoExists() {
    when(productRepository.existsById(anyInt())).thenReturn(Single.just(true));

    TestObserver testObserver = productServiceImpl.save(productDto).test();

    testObserver.assertComplete();
    testObserver.assertNoErrors();
    testObserver.assertNoValues();
  }

  @Test
  @DisplayName("Update product success when exists")
  public void updateProductSuccessWhenExists() {
    when(productRepository.existsById(anyInt())).thenReturn(Single.just(false));

    TestObserver testObserver = productServiceImpl.update(productDto).test();

    testObserver.assertComplete();
    testObserver.assertNoErrors();
    testObserver.assertNoValues();
  }

  @Test
  @DisplayName("Delete product success when exists")
  public void deleteProductSuccessWhenExists() {
    when(productRepository.deleteById(anyInt())).thenReturn(Completable.complete());

    TestObserver testObserver = productServiceImpl.delete(product.getProductId()).test();

    testObserver.assertComplete();
    testObserver.assertNoErrors();
    testObserver.assertNoValues();
  }

  @Test
  @DisplayName("Find product success when exists")
  public void findProductSuccessWhenExists() {
    when(productRepository.findById(anyInt())).thenReturn(Maybe.just(product));

    TestObserver testObserver = productServiceImpl.findById(product.getProductId()).test();

    testObserver.assertComplete();
    testObserver.assertNoErrors();

  }



  public static final String PRODUCT_TEST = "feature/productTest.json";
  public static <T> T fromFileFactory(String path, Class<T> classObject)
      throws IOException {
    return new ObjectMapper().readValue(new ClassPathResource(path).getInputStream(),
        classObject);
  }

}