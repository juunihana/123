public class Answer {

  private String text;
  private int phraseId;
  private int nextPhrase;

  public Answer(String text, int phraseId, int nextPhrase) {
    this.text = text;
    this.phraseId = phraseId;
    this.nextPhrase = nextPhrase;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getPhraseId() {
    return phraseId;
  }

  public void setPhraseId(int phraseId) {
    this.phraseId = phraseId;
  }

  public int getNextPhrase() {
    return nextPhrase;
  }

  public void setNextPhrase(int nextPhrase) {
    this.nextPhrase = nextPhrase;
  }
}
