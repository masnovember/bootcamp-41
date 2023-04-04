package com.nttdata.bank.wallet.bankwallet.business.entity;

import lombok.Data;

@Data
public class TransactionWalletDto {
  private Integer transactionId;
  private String transactionType;
  private String mobileNumberOrigin;
  private String mobileNumberDestination;
  private double transactionAmount;
}
