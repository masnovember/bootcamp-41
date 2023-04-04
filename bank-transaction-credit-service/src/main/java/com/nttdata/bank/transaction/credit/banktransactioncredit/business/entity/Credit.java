package com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class Credit {
  private Integer creditId;
  private String creditNumber;
  private Client client;
  private Product product;
  private Float creditLine;
  private Integer creditMonths;
  private Float creditBalance;
  private List<Account> accountList;
}