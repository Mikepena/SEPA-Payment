import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    //simulierung einer Datenbank wo Bankkonten gespeichert werden
    List<BankAccount> bankAccountList = new ArrayList<>();
    BankAccount myBankAccount = new BankAccount("Mike", "Raiffeisen", "AT", "91", "32000", "00002153291");
    BankAccount otherBankAccount = new BankAccount("Peter", "Volksbank", "AT", "20","15000", "03948120342");
    bankAccountList.add(myBankAccount);
    bankAccountList.add(otherBankAccount);


    Scanner scanner = new Scanner(System.in);
    CheckIBAN ibanChecker = new CheckIBAN();
    CheckSEPA sepaChecker = new CheckSEPA();
    Service service = new Service();
    System.out.println("Your SEPA Payment Service");
    System.out.println("Enter your IBAN");
    String recipientIBAN = scanner.nextLine();
    if (!ibanChecker.checkIBANIsCorrectWithAccounts(recipientIBAN, bankAccountList)) {
        System.out.println("IBAN not found, start again");
        System.exit(0);
    }
    System.out.println("Enter Client IBAN");
    String clientIBAN = scanner.nextLine();
    System.out.println("Enter your Name");
    String recipientName = scanner.nextLine();
    System.out.println("Enter Client Name");
    String clientName = scanner.nextLine();
    System.out.println("Enter the amount");
    double amount = scanner.nextDouble();
    System.out.println("Checking if SEPA mandatory fields are OK");
    Payment newPayment = new Payment(recipientIBAN, clientIBAN, recipientName, clientName, amount);
    if(sepaChecker.checkSEPAMandatory(newPayment)) {
        System.out.println("Everything is right, here are the optional fields. You can use purpose of use or Payment Reference not both. If you dont want to enter something, just press Enter.");
        System.out.println("Enter your Adress");
        String recipientAdress = scanner.nextLine();
        scanner.next();
        System.out.println("Enter client Adress");
        scanner.next();
        String clientAdress = scanner.nextLine();
        System.out.println("Enter purpose of use");
        System.out.println("Warning: If you enter purpose of use you can't use payment reference");
        String purposeOfUse = scanner.nextLine();
        System.out.println("Enter payment reference");
        System.out.println("Warning: If you entered purpose of use, you cant use payment reference. Just press enter.");
        String paymentReference = scanner.nextLine();
        System.out.println("Checking optional Fields");
        Payment newPaymentWithOptionals = new Payment(recipientIBAN, clientIBAN, recipientName, clientName, amount, service.transformEmptyStringToOptional(recipientAdress), service.transformEmptyStringToOptional(clientAdress), service.transformEmptyStringToOptional(purposeOfUse), service.transformEmptyStringToOptional(paymentReference));
        if (sepaChecker.checkSEPAOptional(newPaymentWithOptionals)) {
            System.out.println("Everything is correct and the payment will be sent the next day");
        } else {
            System.out.println("Something went wrong, please try again.");

        }
    } else {
        System.out.println("Something went wrong, please try again.");
    }

    }


}
