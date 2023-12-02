import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreditCardProcessor {
    public void processFile(String inputFilePath, String outputFilePath) {
        FileParser parser = getFileParser(inputFilePath);
        List<CreditCard> creditCards = parser.parseFile(inputFilePath);
         String fileExtension = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);

        if ("json".equalsIgnoreCase(fileExtension)) {
            
            JSONArray outputJsonArray = new JSONArray();
            for (CreditCard card : creditCards) {
                String cardNumber = card.getCardNumber();
                JSONObject cardJson = new JSONObject();
                cardJson.put("CardNumber", card.getCardNumber());
                System.out.println(cardNumber+"**********");
                cardJson.put("CardType", CreditCardFactory.getCardType(cardNumber)); // Assuming CreditCard has getCardType method
                outputJsonArray.put(cardJson);
            }
            // Write the JSON output to the file
            try {
                Files.write(Paths.get(outputFilePath), outputJsonArray.toString(4).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception or log it
            }
        
    }
    else if ("xml".equalsIgnoreCase(fileExtension)) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Root element, e.g., <CreditCards>
            Element root = document.createElement("CreditCards");
            document.appendChild(root);

            for (CreditCard card : creditCards) {
                Element cardElement = document.createElement("CreditCard");

                // Card Number
                Element cardNumberElement = document.createElement("CardNumber");
                cardNumberElement.appendChild(document.createTextNode(card.getCardNumber()));
                cardElement.appendChild(cardNumberElement);

                // Card Type
                String cardType = CreditCardFactory.getCardType(card.getCardNumber());
                Element cardTypeElement = document.createElement("CardType");
                cardTypeElement.appendChild(document.createTextNode(cardType));
                cardElement.appendChild(cardTypeElement);

                // Additional fields (assuming these methods exist in your CreditCard class)
                // Card Holder
                Element cardHolderElement = document.createElement("CardHolder");
                cardHolderElement.appendChild(document.createTextNode(card.getCardHolderName()));
                cardElement.appendChild(cardHolderElement);

                // Expiration Date
                Element expirationDateElement = document.createElement("ExpirationDate");
                expirationDateElement.appendChild(document.createTextNode(card.getExpirationDate()));
                cardElement.appendChild(expirationDateElement);

                // Append this card to the root element
                root.appendChild(cardElement);
            }

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(outputFilePath));

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
        else {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("CardNumber, CardType\n");
            for (CreditCard card : creditCards) {
                String cardNumber = card.getCardNumber();
                String cardType = CreditCardFactory.getCardType(cardNumber); // This will have either the card type or the error message

                // Write the card number and type/error message
                writer.write(cardNumber + ", " + cardType + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }}
    }

    private String formatOutputLine(String cardNumber, String cardType) {

        return cardNumber + "," + cardType;

    }

    private FileParser getFileParser(String filePath) {
        if (filePath.endsWith(".csv")) {
            System.out.println(filePath);
            return new CSVParser();
        } else if (filePath.endsWith(".json")) {
            return new JSONParser();
        }
        else if (filePath.endsWith(".xml")) {
            return new XMLFileParser();}
        // Add logic for other file formats...
        throw new IllegalArgumentException("Unsupported file format");
    }

    public static void main(String[] args) {
        String inputFilePath = "./inputs/demo2_inp.json";
        String outputFilePath = "./outputs/demo2_out.json";

        // Create an instance of CreditCardProcessor and process the file
        CreditCardProcessor processor = new CreditCardProcessor();
        processor.processFile(inputFilePath, outputFilePath);
        // Call processFile with command-line arguments or predefined filenames
    }

}
