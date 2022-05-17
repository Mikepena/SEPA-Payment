import java.util.List;
import java.util.Optional;

public class CheckSEPA {



    public boolean checkSEPAMandatory(Payment payment) {
        CheckIBAN IBANChecker = new CheckIBAN();
        if(IBANChecker.checkIBAN(payment.getClientIBAN())) {
            if(IBANChecker.checkIBAN(payment.getRecipientIBAN())) {
                if(checkAccountName(payment.getClientName())) {
                    if(checkAccountName(payment.getRecipientName())) {
                        if(checkAmount(payment.getAmount())) {
                            return true;
                        } else {
                            System.out.println("Ammount wrong");
                            return false;
                        }
                    } else {
                        System.out.println("Recipient Name Wrong");
                        return false;
                    }
                } else {
                    System.out.println("Client Name Wrong");
                    return false;
                }
            } else {
                System.out.println("Recipient IBAN Wrong");
                return false;
            }
        } else {
            System.out.println("Client IBAN Wrong");
            return false;
        }
    }

    public boolean checkSEPAOptional(Payment payment) {
        if(checkAdress(payment.getClientAdress())) {
            if (checkAdress(payment.getRecipientAdress())) {
                if(checkPaymentReference(payment.getPaymentReference())) {
                    if(checkPurposeOfUse(payment.getPurposeOfUse())) {
                        // check ob Zahlungsrefrenz oder Verwendungszweck ausgefüllt sind, wenn beide ausgefüllt dann falsch, wenn nur eins oder beide leer dann korrekt
                        if(payment.getPaymentReference().isEmpty() && payment.getPurposeOfUse().isPresent() || payment.getPaymentReference().isPresent() && payment.getPurposeOfUse().isEmpty() || payment.getPurposeOfUse().isEmpty() && payment.getPaymentReference().isEmpty()) {
                            return true;
                        } else {
                            System.out.println("Payment Reference and Purpose of Use cannot be used both");
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkAccountName(String accountName) {
        if(accountName.length() <= 70) {
            return true;
        } else {
            System.out.println("Name cannot be more than 70 characters");
            return false;
        }

    }

    public boolean checkAmount(Double amount) {
        String[] splittedAmount = amount.toString().split("\\.");
        if(amount > 0 && splittedAmount[1].length() <= 2) {
            return true;
        } else if (amount <= 0){
            System.out.println("Amount cannot be under 0");
            return false;
        } else if (splittedAmount[1].length() > 2){
            System.out.println("Decimals cannot be more than 2");
            return false;
        } else {
            System.out.println("Amount is wrong");
            return false;
        }

    }

    public boolean checkAdress(Optional<String> adress) {
        if (adress.isEmpty()) {
            System.out.println("Info: Adress is empty");
            return true;
        } if(adress.get().length() <= 70) {
            return true;
        } else {
            System.out.println("Adress is Wrong, cannot be more than 70 characters");
            return false;
        }
    }

    public boolean checkPurposeOfUse(Optional<String> purposeOfUse) {
        if (purposeOfUse.isEmpty()) {
            System.out.println("Info: Purpose Of Use is empty");
            return true;
        }
        if(purposeOfUse.get().length() <= 140 && purposeOfUse.get().matches("[a-zA-Z0-9]+")) {
            return true;
        } else {
            System.out.println("Purpose of Use is Wrong, cannot be more than 140 characters or is not alphanumeric");
            return false;
        }
    }
    public boolean checkPaymentReference(Optional<String> paymentReference) {
        if (paymentReference.isEmpty()) {
            System.out.println("Info: Payment Reference is empty");
            return true;
        }
        if(paymentReference.get().length() <= 35 && paymentReference.get().matches("[a-zA-Z0-9]+")) {
            return true;
        } else {
            System.out.println("Payment Reference is Wrong, cannot be more than 35 characters or is not alpha numeric");
            return false;
        }
    }



}
