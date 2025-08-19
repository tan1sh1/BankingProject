package Entities;

import enums.AccountType;

import java.math.BigDecimal;

public class CurrentAccount extends Account{

    public static final BigDecimal InterestRate=new BigDecimal(4);
    public static final BigDecimal minimumbalance = new BigDecimal(0);

    public CurrentAccount(String accountNo, String customerId, BigDecimal initialBalance){
        super();
        setType(AccountType.CURRENT);

    }

    public CurrentAccount(String accountnum, String CID, AccountType type, BigDecimal balance) {
        super(accountnum, CID, AccountType.CURRENT, balance);
    }

    @Override
    public BigDecimal getInterestRate() {
        return null;
    }

    @Override
    public BigDecimal getMinimalBalance() {
        return null;
    }

    @Override
    public String toString() {
        return "Current Account{" +
                "accountnum='" + getAccountnum() + '\'' +
                ", CID='" + getCID() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
