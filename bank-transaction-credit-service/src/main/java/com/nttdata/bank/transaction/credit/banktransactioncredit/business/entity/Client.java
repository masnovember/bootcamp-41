package com.nttdata.bank.transaction.credit.banktransactioncredit.business.entity;

import lombok.Data;

@Data
public class Client {

  private Integer clientId;
  private String clientName;
  private String clientType;
  private String clientDocument;
  private String clientProfile;
}
