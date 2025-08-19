package enums;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer");


    private final String displayName;

    TransactionType(String displayName){
        this.displayName=displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "displayName='" + displayName + '\'' +
                '}';
    }
}
