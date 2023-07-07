import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ParserApp {

    public static void main(String[] args) throws IOException {
        List<String> sourceLines = Files.lines(Paths.get("src/source.hml")).collect(Collectors.toList());

        List<Element> classes = new ArrayList<>();

        int lineIndex = 1;
        for (String line : sourceLines) {
            if (line.contains(";")) {
                line = line.trim().substring(0, line.indexOf(";"));
            }
            if (!line.startsWith("[") || !line.endsWith("]")) {
                System.out.println("Syntax error at line " + lineIndex + ": brackets expected");
                break;
            }

            Element element = new Element();
            element.tags = new HashMap<>();

            if (line.split(" ")[0].contains("=")) {
                System.out.println("Syntax error at line " + lineIndex + ": no element name");
                break;
            }

            line = line.replace("[", "").replace("]", "");

            element.name = line.split(" ")[0];

            List<String> lexems = Arrays.stream(line.split(" "))
                    .filter(lexem -> lexem.contains("="))
                    .collect(Collectors.toList());

            for (String lexem : lexems) {
                element.tags.put(lexem.split("=")[0], lexem.split("=")[1].replaceAll("\"", ""));
            }

            classes.add(element);

            lineIndex++;
        }

        for (Element element : classes) {
            System.out.println("Element name: " + element.name + ", tags: " + element.tags);
        }
    }

    private static class Element {
        public String name;
        public Map<String, String> tags;
    }
}
