public class BankAccount {

    private String name;
    private String bankName;
    private String country;
    private String checkDigits;
    private String bankCodeNumber;
    private String bankAccountNumber;

    public BankAccount(String name, String bankName, String country, String checkDigits, String bankCodeNumber, String bankAccountNumber) {
        this.name = name;
        this.bankName = bankName;
        this.country = country;
        this.checkDigits = checkDigits;
        this.bankCodeNumber = bankCodeNumber;
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCheckDigits() {
        return checkDigits;
    }

    public void setCheckDigits(String checkDigits) {
        this.checkDigits = checkDigits;
    }

    public String getBankCodeNumber() {
        return bankCodeNumber;
    }

    public void setBankCodeNumber(String bankCodeNumber) {
        this.bankCodeNumber = bankCodeNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
