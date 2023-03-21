package com.nttdata.bank.client.bankclient.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Parameters {

  public static final String URL_MS_ACCOUNT = "http://localhost:8003";
  public static final String URL_MS_CREDIT = "http://localhost:8004";
  public static final String CLIENT_TYPE_PERSONAL = "P";
  public static final String TYPE_PROFILE_PERSONAL = "VIP";
  public static final String TYPE_PROFILE_BUSINESS = "PYME";
  public static final Integer ACCOUNT_AVERAGE_BALANCE = 500;
  public static final Integer CODE_CURRENT_ACCOUNT = 2;
  public static final Integer CODE_PERSONAL_CREDIT_CARD = 6;
  public static final Integer CODE_BUSINESS_CREDIT_CARD = 7;

  public static List<Integer> getCodeCreditCard() {
    return new ArrayList<>(Arrays.asList(CODE_PERSONAL_CREDIT_CARD,
                                         CODE_BUSINESS_CREDIT_CARD));
  };





}
