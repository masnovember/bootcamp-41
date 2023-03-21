package com.nttdata.bank.credit.bankcredit.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "credits")
public class Credit {

  @Id
  private Integer creditId;
  private String creditNumber;
  private Integer clientId;
  private Integer productId;
  private Float creditLine;
  private Integer creditMonths;
  private Float creditBalance;
  private Date creditDateOpen;
  private Date creditDateClose;

}