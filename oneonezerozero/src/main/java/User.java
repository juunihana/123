public class User {

  private int id;
  private String name;
  private boolean isPlayer;
  private int initPhraseId;

  public User(int id, String name, boolean isPlayer, int initPhraseId) {
    this.id = id;
    this.name = name;
    this.isPlayer = isPlayer;
    this.initPhraseId = initPhraseId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isPlayer() {
    return isPlayer;
  }

  public void setPlayer(boolean player) {
    isPlayer = player;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getInitPhraseId() {
    return initPhraseId;
  }

  public void setInitPhraseId(int initPhraseId) {
    this.initPhraseId = initPhraseId;
  }
}
