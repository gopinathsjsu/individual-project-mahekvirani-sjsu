import java.util.List;
public interface FileParser {
    List<CreditCard> parseFile(String filePath);
}