package Entities;

import enums.AccountType;

import java.math.BigDecimal;

public class SavingsAccount extends Account{


    public static final BigDecimal InterestRate=new BigDecimal(6);
    public static final BigDecimal minimumbalance = new BigDecimal(1000);

    @Override
    public BigDecimal getInterestRate() {
        return InterestRate;
    }

    @Override
    public BigDecimal getMinimalBalance() {
        return minimumbalance;
    }

    public SavingsAccount(String accountNo, String customerId, String balance) {
        super();
        setType(AccountType.SAVINGS);
    }

    public SavingsAccount(String accountnum, String CID, AccountType type, BigDecimal balance) {
        super(accountnum, CID, AccountType.SAVINGS, balance);
    }

    @Override
    public String toString() {
        return "Savings Account{" +
                "accountnum='" + getAccountnum() + '\'' +
                ", CID='" + getCID() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
