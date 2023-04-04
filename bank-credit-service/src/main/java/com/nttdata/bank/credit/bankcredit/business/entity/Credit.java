package com.nttdata.bank.credit.bankcredit.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "credits")
public class Credit {

  @Id
  private Integer creditId;
  private String creditNumber;
  private Client client;
  private Product product;
  private Float creditLine;
  private Integer creditMonths;
  private Float creditBalance;
  private List<Account> accountList;

}