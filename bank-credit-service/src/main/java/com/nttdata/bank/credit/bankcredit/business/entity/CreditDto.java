package com.nttdata.bank.credit.bankcredit.business.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreditDto {

  private Integer creditId;
  private String creditNumber;
  private Client client;
  private Product product;
  private Float creditLine;
  private Integer creditMonths;
  private Float creditBalance;
  private List<Account> accountList;

}