package com.nttdata.bank.transaction.account.banktransactionaccount.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Data
@Document(collection = "transactions_accounts")
public class TransactionAccount {
  @Id
  private Integer transactionId;
  private String transactionType;
  private Account account;
  private Float transactionAmount;
  private LocalDate transactionDate;
}