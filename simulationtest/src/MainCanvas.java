import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JPanel;

public class MainCanvas extends JPanel {

  private final Engine engine;

  int mxg, myg;

  private boolean drawBuildingsPage = false;
  private boolean drawBuildingsHover = false;

  private boolean drawUnitsPage = false;
  private boolean drawUnitsHover = false;

  private int currentPage = 0;

  public MainCanvas(Engine engine) {
    this.engine = engine;

    addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx >= 5 && mx <= 105 && my >= 5 && my <= 35) {
          currentPage = 0;
          drawBuildingsPage = !drawBuildingsPage;
          if (drawUnitsPage) {
            drawUnitsPage = false;
          }
        }

        if (mx >= 115 && mx <= 215 && my >= 5 && my <= 35) {
          currentPage = 0;
          drawUnitsPage = !drawUnitsPage;
          if (drawBuildingsPage) {
            drawBuildingsPage = false;
          }
        }

        if (drawBuildingsPage || drawUnitsPage) {
          if (mx >= engine.getFrame().getWidth() - 260 && mx <= engine.getFrame().getWidth() - 210
              &&
              my >= 10 && my <= 30) {
            if (currentPage > 0) {
              currentPage--;
            }
          }

          if (mx >= engine.getFrame().getWidth() - 160 && mx <= engine.getFrame().getWidth() - 110
              &&
              my >= 10 && my <= 30) {
            if (drawBuildingsPage) {
              if (currentPage < engine.getGame().getBuildings().size() / 20) {
                currentPage++;
              }
            }
            if (drawUnitsPage) {
              if (currentPage < engine.getGame().getUnits().size() / 20) {
                currentPage++;
              }
            }
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    });

    addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {

      }

      @Override
      public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        mxg = e.getX();
        myg = e.getY();
        drawBuildingsHover = mx >= 5 && mx <= 100 && my >= 5 && my <= 30;
        drawUnitsHover = mx >= 115 && mx <= 215 && my >= 5 && my <= 35;
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.setColor(Color.LIGHT_GRAY);
    g.drawRect(engine.getFrame().getWidth() - 60, 10, 50, 20);
    g.drawString(engine.getGame().getClock().toString(), engine.getFrame().getWidth() - 55, 25);

    if (drawBuildingsPage || drawUnitsPage) {
      g.drawRect(engine.getFrame().getWidth() - 260, 10, 50, 20);
      g.drawString("<", engine.getFrame().getWidth() - 255, 25);

      g.drawRect(engine.getFrame().getWidth() - 205, 10, 40, 20);
      g.drawString(String.valueOf(currentPage + 1), engine.getFrame().getWidth() - 200, 25);

      g.drawRect(engine.getFrame().getWidth() - 160, 10, 50, 20);
      g.drawString(">", engine.getFrame().getWidth() - 155, 25);
    }

    if (drawBuildingsHover) {
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(5, 10, 100, 20);
      g.setColor(Color.DARK_GRAY);
      g.drawString("Buildings", 10, 25);
    } else {
      g.setColor(Color.LIGHT_GRAY);
      g.drawRect(5, 10, 100, 20);
      g.drawString("Buildings", 10, 25);
    }

    if (drawUnitsHover) {
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(115, 10, 100, 20);
      g.setColor(Color.DARK_GRAY);
      g.drawString("Units", 120, 25);
    } else {
      g.setColor(Color.LIGHT_GRAY);
      g.drawRect(115, 10, 100, 20);
      g.drawString("Units", 120, 25);
    }

    g.setColor(Color.LIGHT_GRAY);

//    for (int i = 0; i < engine.getGame().getUnits().size(); i++) {
//      drawUnitCart(g, engine.getGame().getUnits().get(i), 0, i * 110);
//    }

    if (drawBuildingsPage) {
      drawBuildingsPage(g, engine.getGame().getBuildings(), currentPage);
    }

    if (drawUnitsPage) {
      drawUnitsPage(g, engine.getGame().getUnits(), currentPage);
    }

//    int padding = 5;
//    for (int i = 0; i < engine.getGame().getBuildings().size(); i++) {
//      drawBuildingCart(g, engine.getGame().getBuildings().get(i), i * 10, i * 10,  padding);
//    }

    g.setColor(Color.LIGHT_GRAY);
//    g.drawRect(engine.getFrame().getWidth() - 270, 10, 100, 20);
//    g.drawString("Lumber: " + engine.getGame().getLumber(), engine.getFrame().getWidth() - 265, 25);
//    g.drawRect(engine.getFrame().getWidth() - 165, 10, 100, 20);
//    g.drawString("Metal: " + engine.getGame().getMetal(), engine.getFrame().getWidth() - 160, 25);

    int index = 0;
    for (ResourceType type : engine.getGame().getResources().keySet()) {
      g.drawRect(10 + index * 110, engine.getFrame().getHeight() - 58, 100, 20);
      g.drawString(type.toString() + " " + engine.getGame().getResources().get(type),
          15 + index * 110, engine.getFrame().getHeight() - 45);
      index++;
    }
  }

  private void drawUnitCart(Graphics g, Unit unit, int x, int y, int padding) {
//    int baseXOffset = 10;
//    int innerMargin = 5;
//    g.setColor(Color.LIGHT_GRAY);
//    g.drawRect(baseXOffset + xOffset, 10 + yOffset, 200, 100);
//
//    g.setColor(Color.LIGHT_GRAY);
//    g.fillRect(baseXOffset + 5 + xOffset, 5 + yOffset, 30, 14);
//
//    g.setColor(Color.DARK_GRAY);
//
//    g.setFont(new Font("Courier New", Font.BOLD, 12));
//    g.drawString("Unit", baseXOffset + 6 + xOffset, 16 + yOffset);
//
//    g.setColor(Color.LIGHT_GRAY);
//
//    g.setFont(new Font("Courier New", Font.PLAIN, 16));
//    g.drawString(unit.getName(), baseXOffset + innerMargin + xOffset, 40 + yOffset);
//
//    g.setFont(new Font("Courier New", Font.PLAIN, 12));
//    g.drawString("Job status: " + (unit.getOccupation() == UnitOccupation.EMPLOYED ?
//            "working at " + unit.getBuilding().getProducedResource() : "unemployed"),
//        baseXOffset + innerMargin + xOffset, 60 + yOffset);
//    g.drawString("Now: " + unit.getState().toString(), baseXOffset + innerMargin + xOffset,
//        80 + yOffset);
    //-----old

    //-----new
    g.setColor(Color.LIGHT_GRAY);
    g.drawRect(x, y, 230, 120);

    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y - 5, 60, 14);

    g.setColor(Color.DARK_GRAY);

    g.setFont(new Font("Courier New", Font.BOLD, 12));
    g.drawString("Unit", x + 6, y + 6);

    g.setColor(Color.LIGHT_GRAY);

    g.setFont(new Font("Courier New", Font.PLAIN, 16));
    g.drawString(unit.getName(), x + padding, y + padding + 20);

    g.setFont(new Font("Courier New", Font.PLAIN, 12));
    g.drawString("Job status: " + (unit.getOccupation() == UnitOccupation.EMPLOYED ?
            "working at " + unit.getBuilding().getProducedResource() : "unemployed"), x + padding,
        y + padding + 40);
    g.drawString("Now: " + unit.getState().toString(),
        x + padding, y + padding + 60);
  }

  private void drawUnitsPage(Graphics g, List<Unit> units, int page) {
    final int PAGE_SIZE = 20;
    int firstElement = page * PAGE_SIZE;
    int lastElement = (page + 1) * PAGE_SIZE - 1;
    if (lastElement > units.size() - 1) {
      lastElement = units.size() - 1;
    }

    List<Unit> unitsPage = units.subList(firstElement, lastElement + 1);

    int cardWidth = 230, cardHeight = 120;
    int margin = 10, padding = 5;
    int cardX, cardY, rowCount = 0, colCount = 0;
    int cols = 5, rows = 6;
    for (int i = 0; i < unitsPage.size(); i++) {
      if (i != 0 && i % cols == 0) {
        rowCount++;
        colCount = 0;
      }

      cardX = (cardWidth + margin) * colCount++ + margin;
      cardY = (cardHeight + margin) * rowCount + margin + 30;

      drawUnitCart(g, unitsPage.get(i), cardX, cardY, padding);
    }
  }

  private void drawBuildingsPage(Graphics g, List<Building> buildings, int page) {
    final int PAGE_SIZE = 20;
    int firstElement = page * PAGE_SIZE;
    int lastElement = (page + 1) * PAGE_SIZE - 1;
    if (lastElement > buildings.size() - 1) {
      lastElement = buildings.size() - 1;
    }

    List<Building> buildingsPage = buildings.subList(firstElement, lastElement + 1);

    int cardWidth = 230, cardHeight = 120;
    int margin = 10, padding = 5;
    int cardX, cardY, rowCount = 0, colCount = 0;
    int cols = 8, rows = 6;
    for (int i = 0; i < buildingsPage.size(); i++) {
      if (i != 0 && i % 8 == 0) {
        rowCount++;
        colCount = 0;
      }

      cardX = (cardWidth + margin) * colCount++ + margin;
      cardY = (cardHeight + margin) * rowCount + margin + 30;

      drawBuildingCart(g, buildingsPage.get(i), cardX, cardY, padding);
    }
  }

  private void drawBuildingCart(Graphics g, Building building, int x, int y, int padding) {
    g.setColor(Color.LIGHT_GRAY);
    g.drawRect(x, y, 230, 120);

    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x + 5, y - 5, 60, 14);

    g.setColor(Color.DARK_GRAY);

    g.setFont(new Font("Courier New", Font.BOLD, 12));
    g.drawString("Building", x + 6, y + 6);

    g.setColor(Color.LIGHT_GRAY);

    g.setFont(new Font("Courier New", Font.PLAIN, 16));
    g.drawString(building.getProducedResource().toString(), x + padding, y + padding + 20);

    g.setFont(new Font("Courier New", Font.PLAIN, 12));
    g.drawString("Resource: " + building.getMaxResource(), x + padding, y + padding + 40);
    g.drawString("Schedule: " + building.getBeginsWorkAt() + " to " + building.getEndsWorkAt(),
        x + padding, y + padding + 60);
    g.drawString("Workers: " + building.getEmployeesNumber(),
        x + padding, y + padding + 80);
  }
}
