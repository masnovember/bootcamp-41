package com.nttdata.bank.product.bankproduct.business.entity;

import lombok.Data;

@Data
public class ProductDto {
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
