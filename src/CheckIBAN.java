import java.util.List;

public class CheckIBAN {

    public boolean checkIBANIsCorrectWithAccounts(String iban, List<BankAccount> bankAccounts) {
        for (BankAccount account : bankAccounts) {
            String bankAccountIban = account.getCountry() + account.getCheckDigits() + account.getBankCodeNumber() + account.getBankAccountNumber();
            if(iban.equals(bankAccountIban)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIBAN(String iban) {
        return checkIBANLength(iban) && checkIBANCountryCodeAustria(iban) && checkIBANDigits(iban) && checkBankCodeNumber(iban) && checkAccountNumber(iban);
    }
    public boolean checkIBANLength(String iban) {
        if(iban.length() == 20) {
            return true;
        } else {
            System.out.println("IBAN Length is not 20");
            return false;
        }
    }
    public boolean checkIBANCountryCodeAustria(String iban) {
        if(iban.startsWith("AT")) {
            return true;
        } else {
            System.out.println("Country code not AT");
            return false;
        }
    }

    public boolean checkIBANDigits(String iban) {
        int digits;
        try {
            digits = Integer.parseInt(iban.substring(2, 4));
        } catch (NumberFormatException e) {
            System.out.println("Check digits not numeric");
            return false;
        }
        if(digits >= 1 && digits <= 97) {
            return true;
        } else {
            System.out.println("Check Digits not in range");
            return false;
        }

    }

    public boolean checkBankCodeNumber(String iban) {
        int bankCodeNumber;
        try {
            bankCodeNumber = Integer.parseInt(iban.substring(4, 9));
        } catch (NumberFormatException e) {
            System.out.println("Bank code number not numeric");
            return false;
        }
        if(bankCodeNumber >= 10000 && bankCodeNumber <= 99999) {
            return true;
        } else {
            System.out.println("Bank code number not in range");
            return false;
        }
    }

    public boolean checkAccountNumber(String iban) {
        int accountNumber;
        try {
            accountNumber = Integer.parseInt(iban.substring(10, 19));
        } catch (NumberFormatException e) {
            System.out.println("Account number not numeric");
            return false;
        } if(accountNumber > 0) {
            return true;
        } else {
            System.out.println("Account number not in range");
            return false;
        }
    }

}
