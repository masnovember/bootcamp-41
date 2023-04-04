package com.nttdata.bank.transaction.account.banktransactionaccount.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;

@Data
public class TransactionAccountDto {
  private Integer transactionId;
  private String transactionType;
  private Account account;
  private Float transactionAmount;

  @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate transactionDate;
}