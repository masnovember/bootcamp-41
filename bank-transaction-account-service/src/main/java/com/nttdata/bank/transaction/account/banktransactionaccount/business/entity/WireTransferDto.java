package com.nttdata.bank.transaction.account.banktransactionaccount.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
@Setter
@Getter
@Data
public class WireTransferDto {
  @Id
  private Integer transactionId;
  private String accountNumberOrigin;
  private String accountNumberDestination;
  private Float transactionAmount;

  @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate transactionDate;
}
