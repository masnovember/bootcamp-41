package com.nttdata.bank.account.bankaccount.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

  private Integer clientId;
  private String clientName;
  private String clientType;
  private String clientDocument;

}
