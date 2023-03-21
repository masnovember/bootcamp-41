package com.nttdata.bank.transaction.account.banktransactionaccount.business.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Setter
@Getter
@Data
public class WireTransferDto {
  @Id
  private Integer transactionId;
  private Integer clientIdOrigin;
  private String accountNumberOrigin;
  private String accountNumberDestination;
  private Float transactionAmount;
  private Date transactionDate;
}
