import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class XMLFileParser implements FileParser {

    @Override
    public List<CreditCard> parseFile(String filePath) {
        List<CreditCard> creditCards = new ArrayList<>();

        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("CARD"); // Adjusted to match the input XML

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element eElement = (Element) nList.item(temp);

                String cardNumber = eElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
                String cardHolder = eElement.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent();
                String expirationDate = eElement.getElementsByTagName("EXPIRATION_DATE").item(0).getTextContent();

                CreditCard card = new CreditCard(cardNumber, cardHolder, expirationDate);
                creditCards.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return creditCards;
    }
}
