public class CreditCardFactory {
    public static String getCardType(String cardNumber) {
        if (cardNumber.length() > 19) {
            return "Invalid: Card number exceeds maximum length";
        }

        if (isValidVisa(cardNumber)) {
            return "Visa";
        } else if (isValidMasterCard(cardNumber)) {
            return "MasterCard";
        } else if (isValidAmericanExpress(cardNumber)) {
            return "AmericanExpress";
        } else if (isValidDiscovery(cardNumber)) {
            return "Discovery";
        } 
        else {
            return "Invalid";
        }
    }
    public static CreditCard createCreditCard(String cardNumber, String cardHolder, String expirationDate) {
        if (isValidVisa(cardNumber)) {
            return new VisaCC(cardNumber, cardHolder, expirationDate);
        } else if (isValidMasterCard(cardNumber)) {
            return new MasterCC(cardNumber, cardHolder, expirationDate);
        } else if (isValidAmericanExpress(cardNumber)) {
            return new AmExCC(cardNumber, cardHolder, expirationDate);
        }else if (isValidDiscovery(cardNumber)) {
            return new DiscoveryCC(cardNumber, cardHolder, expirationDate);
        } 
        else {
            throw new IllegalArgumentException("Invalid credit card number");
        }
    }

    private static boolean isValidVisa(String cardNumber) {
        System.out.println();
        return cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16);
    }

    private static boolean isValidMasterCard(String cardNumber) {
        return cardNumber.startsWith("5") && cardNumber.length() == 16;
    }

    private static boolean isValidAmericanExpress(String cardNumber) {
        return cardNumber.startsWith("3") && (cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '7') && cardNumber.length() == 15;
    }
    private static boolean isValidDiscovery(String cardNumber) {
        // Discovery card starts with 6011 and is 16 digits long
        return cardNumber.startsWith("6011") && cardNumber.length() == 16;
    }

    
}
