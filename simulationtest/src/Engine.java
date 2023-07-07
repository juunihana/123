import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Engine {

  private final SimulationGame game;
  private final JFrame mainFrm;
  private final MainCanvas canvas;

  public Engine(SimulationGame game) {
    this.game = game;
    mainFrm = new JFrame("Simulation Game");
    mainFrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainFrm.setLayout(null);
    mainFrm.setSize(1280, 720);

    canvas = new MainCanvas(this);
    canvas.setBounds(0, 0, mainFrm.getWidth(), mainFrm.getHeight() - 28);

    mainFrm.setResizable(false);

    mainFrm.getContentPane().add(canvas);
    mainFrm.setVisible(true);
  }

  public void render() {
    canvas.repaint();
  }

  public SimulationGame getGame() {
    return this.game;
  }

  public JFrame getFrame() {
    return this.mainFrm;
  }
}
