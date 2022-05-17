import java.util.Optional;

public class Payment {

    private String recipientIBAN;
    private String clientIBAN;
    private String recipientName;
    private String clientName;
    private double amount;
    private Optional<String> recipientAdress;
    private Optional<String> clientAdress;
    private Optional<String> purposeOfUse;
    private Optional<String> paymentReference;

    public Payment(String recipientIBAN, String clientIBAN, String recipientName, String clientName, double amount) {
        this.recipientIBAN = recipientIBAN;
        this.clientIBAN = clientIBAN;
        this.recipientName = recipientName;
        this.clientName = clientName;
        this.amount = amount;
    }

    public Payment(String recipientIBAN, String clientIBAN, String recipientName, String clientName, double amount, Optional<String> recipientAdress, Optional<String> clientAdress, Optional<String> purposeOfUse, Optional<String> paymentReference) {
        this.recipientIBAN = recipientIBAN;
        this.clientIBAN = clientIBAN;
        this.recipientName = recipientName;
        this.clientName = clientName;
        this.amount = amount;
        this.recipientAdress = recipientAdress;
        this.clientAdress = clientAdress;
        this.purposeOfUse = purposeOfUse;
        this.paymentReference = paymentReference;
    }

    public String getRecipientIBAN() {
        return recipientIBAN;
    }

    public void setRecipientIBAN(String recipientIBAN) {
        this.recipientIBAN = recipientIBAN;
    }

    public String getClientIBAN() {
        return clientIBAN;
    }

    public void setClientIBAN(String clientIBAN) {
        this.clientIBAN = clientIBAN;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Optional<String> getRecipientAdress() {
        return recipientAdress;
    }

    public void setRecipientAdress(Optional<String> recipientAdress) {
        this.recipientAdress = recipientAdress;
    }

    public Optional<String> getClientAdress() {
        return clientAdress;
    }

    public void setClientAdress(Optional<String> clientAdress) {
        this.clientAdress = clientAdress;
    }

    public Optional<String> getPurposeOfUse() {
        return purposeOfUse;
    }

    public void setPurposeOfUse(Optional<String> purposeOfUse) {
        this.purposeOfUse = purposeOfUse;
    }

    public Optional<String> getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Optional<String> paymentReference) {
        this.paymentReference = paymentReference;
    }
}
