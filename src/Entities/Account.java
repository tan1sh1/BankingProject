package Entities;

import Exceptions.InsufficientBalanceException;
import Exceptions.InvalidDepositValueException;
import enums.AccountType;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account {
    private String accountnum;
    private String CID;
    private AccountType type;
    private BigDecimal balance;

    public Account(){}

    public Account(String accountnum, String CID, AccountType type, BigDecimal balance) {
        this.accountnum = accountnum;
        this.CID = CID;
        this.type = type;
        this.balance = balance;
    }

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountnum='" + accountnum + '\'' +
                ", CID='" + CID + '\'' +
                ", type=" + type +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountnum, account.accountnum) && Objects.equals(CID, account.CID) && type == account.type && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountnum);
    }

    public synchronized void deposit(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new InvalidDepositValueException("Deposit value must be greater than 0");


        }
        this.balance = this.balance.add(amount);
    }
    public synchronized void withdraw(BigDecimal amount){
        if(amount.compareTo(amount)>0){
            throw new InsufficientBalanceException("insufficient balance");

        }
        this.balance=this.balance.subtract(amount);
    }

    public abstract BigDecimal getInterestRate();
    public abstract BigDecimal getMinimalBalance();

//    public BigDecimal calculateInterst(){
//        return balance.multiply(getInterestRate())
//    }

}
