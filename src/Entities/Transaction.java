package Entities;

import enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private String Tid;
    private BigDecimal amount;
    private String accountnum;
    private LocalDateTime timestamp;
    private TransactionType type;
    private String toAccountNo;


    public Transaction(String transactionId, String accountNo, TransactionType withdraw, BigDecimal amount, LocalDateTime now, String toAccountNo){
    }

    public Transaction(String tid, BigDecimal amount, String accountnum, LocalDateTime timestamp, TransactionType type) {
        this.Tid = tid;
        this.amount = amount;
        this.accountnum = accountnum;
        this.timestamp = timestamp;
        this.type = type;
        this.toAccountNo= toAccountNo;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "Tid='" + Tid + '\'' +
                ", amount=" + amount +
                ", accountnum='" + accountnum + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(Tid, this.Tid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Tid, amount, accountnum, timestamp, type);
    }
}
