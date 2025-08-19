package Entities;

import enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    //private final Object toAccountNo;
    private String transactionId;
    private BigDecimal amount;
    private String accountNo;
    private LocalDateTime timestamp;
    private TransactionType type;
    private String toAccountNo;

    public Transaction(){

    }

    public Transaction(String transactionId, BigDecimal amount, String accountNo, LocalDateTime timestamp, TransactionType type){

    }
    //String transactionId, String accountNo, TransactionType transactionType, BigDecimal amount, LocalDateTime timestamp


    public Transaction(String transactionId, BigDecimal amount, String accountNo, LocalDateTime timestamp, TransactionType type,String toAccountNo) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.accountNo = accountNo;
        this.timestamp = timestamp;
        this.type = type;
        this.toAccountNo = toAccountNo;
    }

    public Transaction(String transactionId, String accountNo, TransactionType transactionType, BigDecimal amount, LocalDateTime now) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.accountNo = accountNo;
        this.type = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", accountNo='" + accountNo + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }

    @Override
    public int hashCode(){
        return Objects.hash(transactionId);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
//        if(getClass())
        if(this == obj){
            return true;
        }
        Transaction transaction  = (Transaction) obj;
        return Objects.equals(transactionId, transaction.transactionId);
    }

}