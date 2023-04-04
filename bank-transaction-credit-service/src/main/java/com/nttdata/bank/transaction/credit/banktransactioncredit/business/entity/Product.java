package com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity;

import lombok.Data;

@Data
public class Product {
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
