package com.nttdata.bank.product.bankproduct.business.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionAccountDto {

  private Integer transactionId;
  private String transactionType;
  private Integer accountId;
  private Float transactionAmount;
  private Date transactionDate;

}