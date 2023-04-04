package com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Account {
  private Integer accountId;
  private String accountNumber;
  private Client client;
  private Product product;
  private String accountCurrency;
  private Float accountBalance;
  private List<String> accountHeadlines = new ArrayList<>();
  private List<String> accountSignatories = new ArrayList<>();
}