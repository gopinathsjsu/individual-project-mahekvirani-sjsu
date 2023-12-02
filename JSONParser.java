import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONParser implements FileParser {

    @Override
    public List<CreditCard> parseFile(String filePath) {
        List<CreditCard> cards = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            JsonNode cardsNode = rootNode.path("cards");
            if (cardsNode.isArray()) {
                for (JsonNode node : cardsNode) {
                    String cardNumber = safeText(node, "cardNumber");
                    String cardHolder = safeText(node, "cardHolderName"); // corrected field name
                    String expirationDate = safeText(node, "expirationDate");

                    // No need to catch the exception here since it should be handled in the CreditCardFactory
                    String cardType = CreditCardFactory.getCardType(cardNumber);
                    cards.add(new CreditCard(cardNumber, cardHolder, cardType));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }

    private String safeText(JsonNode jsonNode, String fieldName) {
        JsonNode node = jsonNode.get(fieldName);
        return node != null ? node.asText() : null;
    }
}
