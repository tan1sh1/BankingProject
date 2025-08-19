package enums;

public enum AccountType {
    SAVINGS("Savings acc") ,
    CURRENT("current acc");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "displayName='" + displayName + '\'' +
                '}';
    }
}
