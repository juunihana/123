public class Phrase {

  private int id;
  private String text;
  private boolean withGoodbye;

  public Phrase(int id, String text, boolean withGoodbye) {
    this.id = id;
    this.text = text;
    this.withGoodbye = withGoodbye;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isWithGoodbye() {
    return withGoodbye;
  }

  public void setWithGoodbye(boolean withGoodbye) {
    this.withGoodbye = withGoodbye;
  }
}
