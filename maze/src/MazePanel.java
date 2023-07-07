import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MazePanel extends JPanel {

  private final int SIZE = 1;
  private int[][] map;
  int r = 0, g = 0, b = 0, c = 0;
  boolean direction = false;
  boolean lineDirection = false;
  int currentLine = 0;
  boolean columnDirection = false;
  int currentColumn = 0;

  public MazePanel(int[][] map) {
    super();
    this.map = map;
  }

  public void setMap(int[][] map) {
    this.map = map;
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    int width = map.length;

    if (direction) {
      if (r > 0) {
        r--;
      } else if (g > 0) {
        g--;
      } else if (b > 0) {
        b--;
      }
    } else {
      if (r < 255) {
        r++;
      } else if (g < 255) {
        g++;
      } else if (b < 255) {
        b++;
      }
    }

    if (r == 255 && g == 255 && b == 255 || r == 0 && g == 0 && b == 0) {
      direction = !direction;
    }

//    Color background = new Color(r, g, b, 255);
//    Color noise;
//    final int noiseAlpha = 50;
//    for (int i = 0; i < width; i++) {
//      for (int j = 0; j < map[i].length; j++) {
//        if (i == 0 || j == 0 || i == map.length - 1 || j == map[i].length - 1) {
//          graphics.setColor(new Color(0, 0, 0, 255));
//          graphics.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
//        } else {
//          graphics.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
//
//          switch (map[i][j]) {
//            case 1:
//              noise = new Color(255, 255, 255, noiseAlpha);
//              break;
//            case 2:
//              noise = new Color(128, 128, 128, noiseAlpha);
//              break;
//            default:
//              noise = new Color(0, 0, 0, noiseAlpha);
//              break;
//          }
//          graphics.setColor(blend(background, noise));
//          graphics.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
//        }
//      }
//    }

//    for (int i = 0; i < map[currentLine].length; i++) {
//      graphics.setColor(new Color(0, 0, 0, 255));
//      graphics.fillRect(i * SIZE, currentLine * SIZE, SIZE, SIZE);
//      for (int j = 0; j < 10; j++) {
//          graphics.setColor(new Color(0, 0, 0, 255 - j * 25));
//
//        int gradientCoord = lineDirection ? (i + j) : (i - j);
//        graphics.fillRect(gradientCoord * SIZE, currentLine * SIZE, SIZE, SIZE);
//      }
//    }

    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 10; j++) {
        graphics.setColor(new Color(0, 0, 0, 255 - j * 25));
        graphics.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
      }
    }



//    for (int i = 0; i < map.length; i++) {
//      graphics.setColor(new Color(0, 0, 0, 255));
//      graphics.fillRect(currentColumn * SIZE, i * SIZE, SIZE, SIZE);
//    }

    if (lineDirection) {
      currentLine--;
    } else {
      currentLine++;
    }

    if (currentLine == 0 || currentLine == map.length - 1) {
      lineDirection = !lineDirection;
    }


    if (columnDirection) {
      currentColumn--;
    } else {
      currentColumn++;
    }

    if (currentColumn == 0 || currentColumn == map[currentColumn - 1].length - 1) {
      columnDirection = !columnDirection;
    }
  }

  private Color blend(Color c1, Color c2) {
    double ta = c1.getAlpha() + c2.getAlpha();
    double w1 = c1.getAlpha() / ta;
    double w2 = c2.getAlpha() / ta;

    double r = w1 * c1.getRed() + w2 * c2.getRed();
    double g = w1 * c1.getGreen() + w2 * c2.getGreen();
    double b = w1 * c1.getBlue() + w2 * c2.getBlue();
    double a = Math.max(c1.getAlpha(), c2.getAlpha());

    return new Color((int) r, (int) g, (int) b, (int) a);
  }

}
