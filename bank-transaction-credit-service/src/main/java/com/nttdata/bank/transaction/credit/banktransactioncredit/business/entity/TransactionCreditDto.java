package com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
public class TransactionCreditDto {
  @Id
  private Integer transactionId;
  private String transactionType;
  private Credit credit;
  private Float transactionAmount;

  @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate transactionDate;
}
