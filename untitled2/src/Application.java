import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

  public static void main(String[] args) throws IOException {
//    if (args.length == 0) {
//      System.err.println("Specify the command name (compile or parse)");
//      return;
//    }
//
//    if (args.length == 1) {
//      System.err.println("Specify the filename");
//      return;
//    }

//    ServerSocket socket = new ServerSocket(6666);
//    Socket clientSocket = socket.accept();
//    DataInputStream is = new DataInputStream(clientSocket.getInputStream());
//    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());

    FileInputStream fis_raw = new FileInputStream("document.hdr"); //Hinode Document Format (raw)
    String rawText = new String(fis_raw.readAllBytes());
    fis_raw.close();
//    String rawText = "{header}Paragraph header{paragraph}Some test content which I would like to encode in Base64{paragraph}Some test content which I would like to encode in Base64";

    Map<String, List<String>> sections = new HashMap<>();

    List<String> sectionsRaw = Arrays.stream(rawText.split("\\{header}"))
        .dropWhile(String::isEmpty).toList();

    int headers = 0;
    for (String section : sectionsRaw) {
      String header = "HEADER_" + headers;

      String tmp = section.split("\\{paragraph}")[0];
      if (!tmp.isEmpty()) {
        header = tmp;
      }

      section = section.replace(tmp, "");

      List<String> paragraphs = Arrays.stream(section.split("\\{paragraph}"))
          .dropWhile(String::isEmpty).toList();

      sections.put(header, paragraphs);
      headers++;
    }

    StringBuilder textBuilder = new StringBuilder();
    for (String header : sections.keySet()) {
      textBuilder.append("[header:'").append(header).append("']");
      for (String paragraph : sections.get(header)) {
        textBuilder.append("[paragraph:'").append(paragraph).append("']");
      }
    }

    String text = textBuilder.toString();

    byte[] tmp = Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8));
    String encoded = new String(tmp);

    FileOutputStream fos = new FileOutputStream("document.hd"); //Hinode Document Format
    fos.write(tmp);
    fos.close();

    FileInputStream fis = new FileInputStream("document.hd");
    byte[] inputFile = fis.readAllBytes();
    fis.close();

    String decoded = new String(Base64.getDecoder().decode(inputFile));

    System.out.println("Encoded: " + encoded);
    System.out.println("Decoded: " + decoded);

    List<String> rawTokens = new ArrayList<>();

    StringBuilder sb = new StringBuilder();
    for (int i = 0, curToken = 0; i < decoded.length(); i++) {
      sb.append(decoded.charAt(i));
      if (decoded.charAt(i) == ']') {
        rawTokens.add(sb.toString());
        sb = new StringBuilder();
      }
    }

    List<Map<String, String>> tokens = new ArrayList<>();
    for (String token : rawTokens) {
      Map<String, String> tmpMap = new HashMap<>();
      token = token.replace("[", "").replace("]", "").trim();

      String[] pairs = token.split(":");
      for (int i = 0; i < pairs.length - 1; i++) {
        tmpMap.put(pairs[i], pairs[i + 1].replace("'", ""));
      }

      tokens.add(tmpMap);
    }
  }
}
