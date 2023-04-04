package com.nttdata.bank.wallet.bankwallet.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "wallet_users")
public class WalletUser {
  @Id
  private Integer walletUserId;
  private String typeIdentificationDocument;
  private String numberIdentificationDocument;
  private String mobileNumber;
  private String mobileImei;
  private String wallerUserEmail;
  private double walletBalance;
}