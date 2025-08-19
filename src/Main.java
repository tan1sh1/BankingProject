import Entities.*;
import Exceptions.InsufficientBalanceException;
import enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static final Map<String , Customer> customers = new HashMap<>();
    public static final Map<String , Account> accounts = new HashMap<>();
    public static final List<Transaction> Transactions = new ArrayList<>();

    private static final Scanner sc = new Scanner(System.in);

    private static final DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    private static final DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh-mm-ss");

    public static void main(String[] args) {
        System.out.println("welcome  to hdfc bank");
        try {
            while (true) {
                showMainMenu();
            }
            }catch (Exception e){
                System.out.println("Error "+e.getMessage());

        }finally{
            sc.close();
        }
    }

    private static void showMainMenu(){
        System.out.println("\n === Main Menu ===");
        System.out.println("1. Register new customer");
        System.out.println("2. Create Account");
        System.out.println("3. Perform Transaction");
        System.out.println("4. View Account Details");
        System.out.println("5. View transaction history");
        System.out.println("6. Exit");

        System.out.println("enter your choice");

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

        }

    }



    private static int getInput(){
        while(true){
            try{
                return Integer.parseInt(sc.nextLine().trim());
            }catch (NumberFormatException e) {
                System.out.println("enter valid number");
            }
        }

    }

    public static void registerCustomer(){
        System.out.println("\n ==Customer Register==");
        System.out.println("Enter Customer Id");
        String customerId = sc.nextLine().trim();

        if(customers.containsKey(customerId)){
            System.out.println("customer Already Exists");
            return;
        }
        System.out.println("enter name");
        String name = sc.nextLine().trim();

        System.out.println("enter email");
        String email = sc.nextLine().trim();

        System.out.println("enter phone");
        String phone = sc.nextLine().trim();

        System.out.println("enter DOB yyyy-mm-dd");
        String DOB = sc.nextLine().trim();

        LocalDate dateOfBirth;
        try {
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateOfBirth = LocalDate.parse(DOB, dateformatter);
        }catch (Exception e){
            System.out.println("invalid date format");
            return;
        }
        System.out.println("Enter Password");
        String password = sc.nextLine().trim();

        Customer customer = new Customer(customerId,name,email,phone,password,dateOfBirth);
        customers.put(customerId,customer);

        System.out.println("customer registered successfully");

    }
    private static void createAccount() {
        System.out.println("\n===create new account===");

        System.out.println("enter customer id");
        String customerId= sc.nextLine();

        Customer customer = customers.get(customerId);
        if(customer==null){
            System.out.println("customer not found");
            return;
        }

        System.out.println("choose account type");
        System.out.println("1. Savings");
        System.out.println("2. Current");
        System.out.println("select acc type");

        int typeChoice = getInput();
        Account account = null;

        System.out.println("enter initial balance");
        String balance = sc.nextLine().trim();

        try{
            BigDecimal initialBalance = new BigDecimal(balance);
            String accountNo = generateAccountNo();
            switch (typeChoice){
                case 1:
                    account = new SavingsAccount(accountNo,customerId,balance);
                    break;
                case 2:
                    account = new CurrentAccount(accountNo,customerId,initialBalance);
                    break;
                default:
                    System.out.println("invalid account type");
                    return;
            }
            accounts.put(accountNo,account);
            System.out.println("congrats succesfull");

        }catch(NumberFormatException e){
            System.out.println("invalid balance");
        }
    }

    private static String generateAccountNo() {
        return String.format("%10d",System.currentTimeMillis());
    }


    private static void performTransaction() {

        System.out.println("\n===Perform Transactions===");
        System.out.println("1. deposit");
        System.out.println("2. withdraw");
        System.out.println("3. transfer");
        System.out.println("select transaction type");

        int transactionchoice = getInput();
        switch (transactionchoice){
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
                System.out.println("invalid transaction");
        }


    }
    private static void performDeposit(){
        System.out.println("enter acc num");
        String accountNo = sc.nextLine().trim();

        Account account = accounts.get(accountNo);
    }
    private static void performWithdraw(){
        System.out.println("Enter account num");
        String accountNo = sc.nextLine().trim();

        Account account = accounts.get(accountNo);

        if(account == null){
            System.out.println("account not registered");
            return;
        }

        System.out.println("enter the withdraw amount");
        String amountstr = sc.nextLine().trim();

        try{
            BigDecimal amount = new BigDecimal(amountstr);

            account.withdraw(amount);
            String transactionId = generateTransactionId();

            Transaction transaction = new Transaction(transactionId,accountNo, TransactionType.WITHDRAW,amount, LocalDateTime.now(), toAccountNo);
            transactions.add(transaction);
            System.out.println("withdrawn succesfully "+account.getBalance());
        }catch (NumberFormatException e){
            System.out.println("invalid amount");
        }catch (InsufficientBalanceException e){
            System.out.println("error "+e.getMessage());
        }

    }
    private static void performTransfer(){
        System.out.println("enter source account no:");
        String fromAccountNo = sc.nextLine().trim();

        Account fromAccount = accounts.get(fromAccountNo);
        if(fromAccount==null){
            System.out.println("source account not found");
            return;
        }
        System.out.println("enter destination acc no");
        String toAccountNo = sc.nextLine().trim();

        Account toAccount = accounts.get(toAccountNo);
        if(toAccount==null){
            System.out.println("destination account not found");
            return;
        }
        if(fromAccountNo.equals(toAccountNo)){
            System.out.println("cant transfer to same account");
        }
        System.out.println("enter amount: ");
        String amountStr = sc.nextLine().trim();

        try{
            BigDecimal amount = new BigDecimal(amountStr);

            fromAccount.withdraw(amount);
            toAccount.deposit(amount);

            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(transactionId,fromAccountNo,TransactionType.TRANSFER,amount,LocalDateTime.now(),toAccountNo);
        }catch(NumberFormatException e){
            System.out.println("invalid amount entered");
        }catch (InsufficientBalanceException e){
            System.out.println("error");
        }



    }



}

