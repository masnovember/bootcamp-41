package com.nttdata.bank.account.bankaccount.business.entity;

import lombok.Data;

@Data
public class Client {

  private Integer clientId;
  private String clientName;
  private String clientType;
  private String clientDocument;
  private String clientProfile;
}
