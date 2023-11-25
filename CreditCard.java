public class CreditCard {
    protected String CardNumber;
    protected String CardHolderName;
    protected String ExpirationDate;

    public CreditCard(String CardNumber, String CardHolderName, String ExpirationDate) {
        this.CardNumber = CardNumber;
        this.CardHolderName = CardHolderName;
        this.ExpirationDate = ExpirationDate;
    }
    public String getCardNumber() {
        return CardNumber;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }
    
}
