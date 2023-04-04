package com.nttdata.bank.transaction.wallet.banktransactionwallet.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Document(collection = "transactions_wallet")
public class TransactionWallet {
  @Id
  private Integer transactionId;
  private String transactionType;
  private String mobileNumberOrigin;
  private String mobileNumberDestination;
  private double transactionAmount;

  @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate transactionDate;
}