import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Application extends JFrame {

  private final int WINDOW_WIDTH = 1280;
  private final int WINDOW_HEIGHT = 720;

  private Dialog dialog;
  private JPanel chatPanel;
  private JTextPane chatTextPane;
  private final List<JButton> answerButtons;

  private final Color foregroundColor = new Color(0xa0a0a0);
  private final Color backgroundColor = new Color(0x333333);
  private final Color playerColor = new Color(0xa0f00a);
  private final Color aiColor = new Color(0xf0a00a);
  private final Color userActionColor = new Color(0xF00A85);

  private final Font font = new Font(Font.MONOSPACED, Font.PLAIN, 14);

  public Application() {
    answerButtons = new ArrayList<>();

    setTitle("Chat");
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setForeground(foregroundColor);
    getContentPane().setBackground(backgroundColor);
    setLayout(null);

    initChat();
    initUsers();

    getContentPane().add(chatPanel);
    setVisible(true);
  }

  public static void main(String[] args) {
    new Application();
  }

  private void initChat() {
    chatPanel = new JPanel();
    chatPanel.setLayout(null);
    chatPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT - 28);
    chatPanel.setForeground(foregroundColor);
    chatPanel.setBackground(backgroundColor);
    chatPanel.setBorder(BorderFactory.createEmptyBorder());

    chatTextPane = new JTextPane();
    chatTextPane.setBounds(0, 5, WINDOW_WIDTH, 400);
    chatTextPane.setBorder(BorderFactory.createLineBorder(foregroundColor));
    chatTextPane.setFont(font);
    chatTextPane.setEditable(false);
    chatTextPane.setForeground(foregroundColor);
    chatTextPane.setBackground(backgroundColor);

    dialog = new Dialog();

    startChat(5);

    chatPanel.add(chatTextPane);
  }

  private void initUsers() {

  }

  private void startChat(int userId) {
    dialog.setCurrentUser(userId);
    showPhrase(dialog.getCurrentUser().getInitPhraseId());
  }

  void showPhrase(int id) {
    answerButtons.forEach(chatPanel::remove);
    answerButtons.clear();
    revalidate();
    repaint();
    appendUserMessage(dialog.getCurrentUser(), dialog.getPhraseById(id).getText());
    int buttonVerticalOffset = 1;
    if (dialog.getAnswersForPhrase(id).isEmpty()) {
      appendUserActionMessage(dialog.getCurrentUser(), "left");
    } else {
      for (Answer answer : dialog.getAnswersForPhrase(id)) {
        JButton answerButton = new JButton(answer.getText());
        answerButton.setBounds(
            0,
            (int) chatTextPane.getSize().getHeight() + 30 * buttonVerticalOffset,
            WINDOW_WIDTH,
            30);

        answerButton.setForeground(foregroundColor);
        answerButton.setBackground(backgroundColor);
        answerButton.setFont(font);
        answerButton.setBorder(BorderFactory.createLineBorder(foregroundColor));

        int nextPhraseId = answer.getNextPhrase();
        answerButton.addActionListener(e -> {
          appendUserMessage(dialog.getPlayerUser(), answer.getText());
          showPhrase(nextPhraseId);
        });
        answerButtons.add(answerButton);
        buttonVerticalOffset++;
      }
      answerButtons.forEach(chatPanel::add);
    }
  }

  private void appendUserMessage(User user, String message) {
    appendText("[", textColor(foregroundColor));
    appendText(user.getName(), textColor(user.isPlayer() ? playerColor : aiColor));
    appendText("]: " + message + "\n", textColor(foregroundColor));
  }

  private void appendUserActionMessage(User user, String action) {
    appendWrappedUsername(user);
    appendText(action + "\n", textColor(userActionColor));
  }

  private void appendText(String text, AttributeSet attributeSet) {
    try {
      chatTextPane.getStyledDocument().insertString(
          chatTextPane.getStyledDocument().getLength(), text, attributeSet);
    } catch (Exception e) {
    }
  }

  private void appendWrappedUsername(User user) {
    appendText("[", textColor(foregroundColor));
    appendText(user.getName(), textColor(user.isPlayer() ? playerColor : aiColor));
    appendText("]", textColor(foregroundColor));
  }

  private AttributeSet textColor(Color c) {
    SimpleAttributeSet attributeSet = new SimpleAttributeSet();
    StyleConstants.setForeground(attributeSet, c);
    return attributeSet;
  }
}
