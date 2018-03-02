package com.bigeek.reactive.constants;

import com.bigeek.reactive.domain.Account;

/**
 * Constant class with some account instances used to initialize DB and testing.
 */
public class Accounts {

    private Accounts() {
        super();
    }

    public static final Account ACCOUNT_1 = Account.builder().accountId(1).personId(1).accountNumber("3781 457886 79824").balance(6100.49).build();
    public static final Account ACCOUNT_2 = Account.builder().accountId(2).personId(1).accountNumber("4532 5408 0495 8431").balance(1125.12).build();
    public static final Account ACCOUNT_3 = Account.builder().accountId(3).personId(2).accountNumber("5427 6051 6363 0817").balance(24101.03).build();
    public static final Account ACCOUNT_4 = Account.builder().accountId(4).personId(2).accountNumber("4485 9985 4693 1107").balance(125.55).build();
    public static final Account ACCOUNT_5 = Account.builder().accountId(5).personId(2).accountNumber("4024 0071 2078 8350").balance(100.0).build();

}
