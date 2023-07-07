public class Application {

  public static void main(String[] args) {
    MarkedProcessor processor = new MarkedProcessor();

    MarkedOne marked = new MarkedOne();
    marked.setValue("Hello World");

    UnmarkedOne unmarked = new UnmarkedOne();
    unmarked.setValue("Goodnight sweet prince");

    System.out.println(processor.checkIfMarked(marked));
    System.out.println(processor.checkIfMarked(unmarked));
  }
}
