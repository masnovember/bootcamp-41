package com.nttdata.bank.account.bankaccount.business.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccountDto {
  private Integer accountId;
  private String accountNumber;
  private Client client;
  private Product product;
  private String accountCurrency;
  private Float accountBalance;
  private List<String> accountHeadlines = new ArrayList<>();
  private List<String> accountSignatories = new ArrayList<>();
}