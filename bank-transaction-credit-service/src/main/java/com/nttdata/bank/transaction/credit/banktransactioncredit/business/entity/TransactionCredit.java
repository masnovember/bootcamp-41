package com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "transactions_credits")
public class TransactionCredit {
  @Id
  private Integer transactionId;
  private String transactionType;
  private Credit credit;
  private Float transactionAmount;
  private LocalDate transactionDate;
}
