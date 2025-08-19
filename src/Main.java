import Entities.*;
import Exceptions.InsufficientBalanceException;
import enums.TransactionType;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

//import Exception.*;

public class Main {
    private static final Map<String, Customer> customers = new HashMap<>();
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final List<Transaction> transactions= new ArrayList<>();

    private static final Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args){
        System.out.println("Welcome to HDFC banking app");
        try {
            while(true){
                showMainMenu();
            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("Error :"+e.getMessage());
        }finally{
            scanner.close();
        }
    }


    private static void showMainMenu() {


        System.out.println("\n == MAIN MENU ==");
        System.out.println("1. Register new customer");
        System.out.println("2. Create account");
        System.out.println("3. Perform Transaction");
        System.out.println("4. view account details");
        System.out.println("5. view transaction history");
        System.out.println("6. Exit");

        System.out.println("Enter your choice ");
        int choice = getInput();

        switch (choice){
            case 1:
                registerCustomer();
                break;
            case 2:
                createAccount();
                break;
            case 3:
                performTransaction();
                break;
            case 4:
                viewAccountDetails();
                break;

            case 5:
                viewTransactionHistory();
                break;

            case 6:
                System.out.println("exiting the system....Goodbye!!!");
                System.exit(0);
                break;

            default:
                System.out.println("invalid choice");


        }

    }
    private static void registerCustomer() {

        System.out.println("----Customer Registration---");
        System.out.println("Enter customer id");
        String customerId  = scanner.nextLine().trim();

        if(customers.containsKey(customerId)){
            System.out.println("Customer already exists");
        }

        System.out.println("Enter name");
        String name = scanner.nextLine().trim();

        System.out.println("Enter email");
        String email = scanner.nextLine().trim();

        System.out.println("Enter phone");
        String phone = scanner.nextLine().trim();

        System.out.println("Enter dob yyyy-MM-dd");
        String dobStr = scanner.nextLine().trim();

        //1LocalDate dateOfBirth;
        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(dobStr, dateFormatter);
        } catch (Exception e) {
            System.out.println("Invalid date format. Registration failed.");
            return;
        }
        Customer customer = new Customer(customerId, name, email, phone, dateOfBirth);
        customers.put(customerId, customer);
        System.out.println("Customer registered successfully.");
    }

    private static void createAccount() {
        System.out.println("Creating new account");
        System.out.println("Please enter customer id");
        String customerId = scanner.nextLine().trim();

        Customer customer = customers.get(customerId);
        if(customer==null){
            System.out.println("Customer not found");
            return;
        }
        System.out.println("choose account type");

        System.out.println("1. Savings Account(6% interest rate and min balance of Rs1000)");
        System.out.println("2. Current Account(4% interest rate and no min balance");
        System.out.println("select type");

        int typeChoice = getInput();
        Account account = null;
        System.out.println("Enter initial balance");
        String  balance = scanner.nextLine().trim();

        try{
            BigDecimal initialBalance = new BigDecimal(balance);
            String accountNo = generateAccountNo();

            switch(typeChoice){
                case 1:
                    account = new SavingsAccount(accountNo, customerId, initialBalance);
                    break;
                case 2:
                    account = new CurrentAccount(accountNo, customerId,initialBalance);
                    break;
                default:
                    System.out.println("invalid");
                    return;
            }
            accounts.put(accountNo,account);
            System.out.println("Congratulations!! accountNo is:"+accountNo);
        }catch(NumberFormatException e){
            System.out.println("invalid balance amount");
        }
    }
    private static String generateAccountNo() {
        return String.format("%10d", System.currentTimeMillis()%10000000000L);
    }





    private static void performTransaction() {
        System.out.println("\n ===perform transaction===");
        System.out.println("1. deposit");
        System.out.println("2. withdraw");
        System.out.println(("3. transfer"));
        System.out.println("select transaction type");

        int transactionChoice = getInput();
        switch(transactionChoice){
            case 1:
                performDeposit();
                break;
            case 2:
                performWithdraw();
                break;
            case 3:
                performTransfer();
                break;
            default:
                System.out.println("Invalid transaction");

        }
    }



    private static void performDeposit() {
        System.out.println("Enter account number");
        String accountNo = scanner.nextLine().trim();

        Account account = accounts.get(accountNo);
        if(account == null){
            System.out.println("Account not found");
            return;
        }

        System.out.println("Enter deposit amount");
        String amountStr = scanner.nextLine().trim();

        try{
            BigDecimal amount = new BigDecimal(amountStr);
            account.deposit(amount);

            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(transactionId,accountNo, TransactionType.DEPOSIT, amount, LocalDateTime.now());
            transactions.add(transaction);

            System.out.println("Deposit was successful! New balance: "+account.getBalance());
        }catch(NumberFormatException e){
            System.out.println("Invalid Amount");
        }
    }


    private static String generateTransactionId(){
        return "HDFC_TXN" + System.currentTimeMillis();
    }

    private static void performWithdraw() {
        System.out.println("Enter account number");
        String accountNo = scanner.nextLine().trim();

        Account account = accounts.get(accountNo);

        if(account == null){
            System.out.println("Account is not registered");
            return;
        }
        System.out.println("Enter the withdrawl amount");
        String amountStr = scanner.nextLine().trim();

        try{
            BigDecimal amount = new BigDecimal(amountStr);

            account.withdraw(amount);
            String transactionId = generateTransactionId();

            Transaction transaction = new Transaction(transactionId,accountNo,TransactionType.WITHDRAW, amount, LocalDateTime.now());
            transactions.add(transaction);

            System.out.println("withdrawal successful");

        }catch(InsufficientBalanceException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private static void performTransfer() {
        System.out.println("enter source account number");
        String fromAccountNo = scanner.nextLine().trim();

        Account fromAccount = accounts.get(fromAccountNo);
        if(fromAccount == null){
            System.out.println("Source account not found");
            return;
        }

        System.out.println("enter destination account number");
        String toAccountNo = scanner.nextLine().trim();

        Account toaccount = accounts.get(toAccountNo);
        if(toaccount == null){
            System.out.println("Source account not found");
            return;
        }

        if(fromAccountNo.equals(toAccountNo)){
            System.out.println("cannot transfer");
        }

        System.out.println("Enter amount");
        String amountStr = scanner.nextLine().trim();
        try{
            BigDecimal amount = new BigDecimal(amountStr);
            fromAccount.withdraw(amount);
            toaccount.deposit(amount);

            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(transactionId, fromAccountNo, TransactionType.TRANSFER, amount, LocalDateTime.now());
            transactions.add(transaction);
            Transaction transaction1 = new Transaction(transactionId, fromAccountNo, TransactionType.TRANSFER, amount, LocalDateTime.now());
            transactions.add(transaction1);

        }catch(NumberFormatException e){
            System.out.println("Invalid amount entered");
        }catch (InsufficientBalanceException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private static void viewAccountDetails() {
        System.out.println("Account details");
        System.out.println("Enter account number");

        String accountNo = scanner.nextLine().trim();
        Account account = accounts.get(accountNo);
        if(account==null){
            System.out.println("account not found");
            return;
        }

        System.out.println(account.toString());
        if(account instanceof SavingsAccount){
            System.out.println("Savings account");
        }else{
            System.out.println("Current Account");
        }
    }


    private static void viewTransactionHistory() {

        System.out.println("Transaction history");
        System.out.println("enter account number");
        String accountNo = scanner.nextLine().trim();

        Account account = accounts.get(accountNo);
        if(account==null){
            System.out.println("account not found");
            return;
        }
        List<Transaction> accountTransactions = transactions.stream()
                .filter(t -> accountNo.equals(t.getAccountNo()))
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .collect(Collectors.toList());

        if(accountTransactions.isEmpty()){
            System.out.println("No transactions found");
            return;
        }
        System.out.println("\nTransactions for Account: "+accountNo);
        for(Transaction transaction: accountTransactions){
            System.out.println(transaction.toString());
        }
        Map<TransactionType, Long> transactionSummary = accountTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType, Collectors.counting()));
        System.out.println("\n == Transaction summary==");
        transactionSummary.forEach((type, count)->
                System.out.println(type.getDisplayName()+":"+count));

//        System.out.println("Transaction history");
//        System.out.println("enter account number");
//        String accountNo = scanner.nextLine().trim();
//
//        Account account = null;
//        if (accounts instanceof List) {
//            account=((List<Account>)accounts).stream()
//                    .filter(acc->acc.getAccountNo().equals(accountNo))
//                    .findFirst()
//                    .orElse(null);
//
//        }
//        if(account==null){
//            System.out.println("account not found");
//            return;
//    }
//        List<Transaction> transaction = transactions.stream()
//                .filter(t->accountNo.equals(t.getAccountNo())||accountNo.equals((t.getAccountNo())))
//                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
//                .toList();
//        if(transaction.isEmpty()){
//            System.out.println("no transaction found");
//            return;
//        }
//
//        System.out.println("\nTransaction for account "+accountNo);
//        for(Transaction t:transaction){
//            System.out.println(t);
//        }
//
//        Map<TransactionType,Long> transactionSummary=transaction.stream()
//                .collect(Collectors.groupingBy(Transaction::getType,Collectors.counting()));
//
//        System.out.println("\n===Transaction Summary===");
//        transactionSummary.forEach((type, count)->
//                System.out.println(type.getDisplayName()+":"+count)
//        );

    }

    private static int getInput(){
        while(true){
            try{
                return Integer.parseInt(scanner.nextLine().trim());
            }catch(NumberFormatException e){
                System.out.println("please enter valid number");

            }
        }
    }

}