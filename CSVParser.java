import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVParser implements FileParser {
    
    @Override
    public List<CreditCard> parseFile(String filePath) {
        List<CreditCard> cards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header row
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    String cardNumber = values[0].trim();
                    String cardType = CreditCardFactory.getCardType(cardNumber);
                    String cardHolder = values[1].trim();
                    String expirationDate = values[2].trim();
                    if (!cardType.startsWith("Invalid")) {
                        cards.add(new CreditCard(cardNumber, cardHolder, cardType));
                    } else {
                        cards.add(new CreditCard(cardNumber, "Error: " + cardType, ""));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return cards;
    }
    }