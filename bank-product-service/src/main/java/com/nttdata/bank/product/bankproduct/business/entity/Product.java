package com.nttdata.bank.product.bankproduct.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
  @Id
  private Integer productId;
  private String productName;
  private String productType;
  private boolean productHaveMaintenance;
  private Float productMaintenance;
  private boolean productHaveLimitMovements;
  private Integer productMonthlyMovements;
  private boolean productHaveDayMovements;
  private Integer productDayMovements;
}
