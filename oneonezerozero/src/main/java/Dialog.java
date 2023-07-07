import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dialog {

  private List<Phrase> phrases;
  private List<Answer> answers;
  private List<User> users;
  private User playerUser;
  private User currentUser;

  private final User tzeench = new User(-1, "tzeench", false, -1);
  private final Phrase tzeenchSays = new Phrase(-1, "wisdom holds no truth", true);
  private final List<Answer> GOODBYE_ANSWERS = List.of(
      new Answer("Goodbye", -1, 999)
  );

  public Dialog() {
    phrases = List.of(
        new Phrase(0, "Hello there", false),
        new Phrase(1, "How are you", true),
        new Phrase(2, "Nice to meet you too", true),

        new Phrase(999, "Bye now", false)
    );

    answers = List.of(
        new Answer("Hi", 0, 1),
        new Answer("Nice to meet you", 0, 2)
    );

    users = List.of(
        new User(1, "bob", false, 0),
        new User(2, "mike", false, 1)
    );

    playerUser = new User(1, "alice", true, -1);

    currentUser = getUserById(1);
  }

//    phrases = new HashMap<>();
//    phrases.put(0, new Phrase("Hello there",
//        List.of(
//            new Answer("Hi", 1),
//            new Answer("Nice to meet you", 2)
//        )));
//
//    phrases.put(1, new Phrase("How are you?",
//        List.of(
//            new Answer("I'm fine, thanks. How are you?", 3),
//            new Answer("Pretty bad, actually", 4)
//        )));
//
//    phrases.put(2, new Phrase("Nice to meet you too",
//        List.of(
//            new Answer("What are you plans tonight?", 5),
//            new Answer("Well... not sure what to say now sry", 6)
//        )));
//
//    phrases.put(3, new Phrase("I'm okay, thanks for asking",
//        List.of(
//
//        )));
//
//    phrases.put(4, new Phrase("Oh, sorry to hear that. What's the matter?",
//        List.of(
//            new Answer("Uhm, my keyboard is not working", 7),
//            new Answer("MMR is just a number", 8),
//            new Answer("gg wp", 9)
//        )));
//
//    phrases.put(5, new Phrase("Going to watch some anime bruh",
//        List.of(
//
//        )));
//
//    phrases.put(6, new Phrase("Until next time then, I guess",
//        List.of(
//
//        )));
//
//    phrases.put(7, new Phrase("zahodit ulitka v bar...",
//        List.of(
//
//        )));
//
//    phrases.put(8, new Phrase("mine is just above 9k lol",
//        List.of(
//
//        )));
//
//    phrases.put(9, new Phrase("u n00b",
//        List.of(
//
//        )));
//
//    phrases.put(999, new Phrase("Bye now", Collections.emptyList()));

  public List<Phrase> getPhrases() {
    return phrases;
  }

  public void setPhrases(List<Phrase> phrases) {
    this.phrases = phrases;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answer> answers) {
    this.answers = answers;
  }

  public List<Answer> getAnswersForPhrase(int phraseId) {
    List<Answer> answersForPhrase = answers.stream()
        .filter(answer -> answer.getPhraseId() == phraseId)
        .collect(Collectors.toList());

    if (getPhraseById(phraseId).isWithGoodbye()) {
      List<Answer> result = new ArrayList<>(answersForPhrase);
      result.addAll(GOODBYE_ANSWERS);
      return result;
    }

    return answersForPhrase;
  }

  public Phrase getPhraseById(int id) {
    return phrases.stream()
        .filter(phrase -> phrase.getId() == id)
        .findFirst().orElse(tzeenchSays);
  }

  public void addUser(String name, int initPhraseId) {
    this.users.add(new User(users.size(), name, false, initPhraseId));
  }

  public List<User> getUsers() {
    return users;
  }

  public User getUserById(int id) {
    return users.stream()
        .filter(user -> user.getId() == id)
        .findAny().orElse(tzeench);
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public User getPlayerUser() {
    return playerUser;
  }

  public void setPlayerUser(User playerUser) {
    this.playerUser = playerUser;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(int id) {
    this.currentUser = getUserById(id);
  }
}
