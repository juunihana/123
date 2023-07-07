import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulationGame {



  private final int MINUTE_INTERVAL = 5;
  private final int FRAME_INTERVAL = 5;

  private List<Building> buildings;
  private List<Unit> units;
  private Clock clock;
  private Map<ResourceType, Integer> resources;
  private Engine engine;
  private int minuteTimer;

  public void init() {
    resources = new HashMap<>();
    resources.put(ResourceType.COAL, new Random().nextInt(50) + 150);
    resources.put(ResourceType.METAL, new Random().nextInt(50) + 150);
    resources.put(ResourceType.LUMBER, new Random().nextInt(50) + 150);
    resources.put(ResourceType.OIL, new Random().nextInt(50) + 150);
    resources.put(ResourceType.HITECH, new Random().nextInt(50) + 150);

    units = new ArrayList<>();
    units.add(new Unit("Aidan", 2));
    units.add(new Unit("Melissa", 1));
    units.add(new Unit("Ian", 1));
    units.add(new Unit("Alice", 2));
    units.add(new Unit("John", 1));
    units.add(new Unit("Bob", 1));
    units.add(new Unit("A", 1));
    units.add(new Unit("B", 1));
    units.add(new Unit("C", 1));
    units.add(new Unit("D", 1));
    units.add(new Unit("E", 1));
    units.add(new Unit("F", 1));
    units.add(new Unit("John", 1));
    units.add(new Unit("Bob", 1));
    units.add(new Unit("A", 1));
    units.add(new Unit("B", 1));
    units.add(new Unit("C", 1));
    units.add(new Unit("D", 1));
    units.add(new Unit("E", 1));
    units.add(new Unit("F", 1));
    units.add(new Unit("John", 1));
    units.add(new Unit("Bob", 1));
    units.add(new Unit("A", 1));
    units.add(new Unit("B", 1));
    units.add(new Unit("C", 1));
    units.add(new Unit("D", 1));
    units.add(new Unit("E", 1));
    units.add(new Unit("F", 1));
    units.add(new Unit("John", 1));
    units.add(new Unit("Bob", 1));
    units.add(new Unit("A", 1));
    units.add(new Unit("B", 1));
    units.add(new Unit("C", 1));
    units.add(new Unit("D", 1));
    units.add(new Unit("E", 1));
    units.add(new Unit("F", 1));
    units.add(new Unit("John", 1));
    units.add(new Unit("Bob", 1));
    units.add(new Unit("A", 1));
    units.add(new Unit("B", 1));
    units.add(new Unit("C", 1));
    units.add(new Unit("D", 1));
    units.add(new Unit("E", 1));
    units.add(new Unit("F", 1));
    units.add(new Unit("John", 1));
    units.add(new Unit("Bob", 1));
    units.add(new Unit("A", 1));
    units.add(new Unit("B", 1));
    units.add(new Unit("C", 1));
    units.add(new Unit("D", 1));
    units.add(new Unit("E", 1));
    units.add(new Unit("F", 1));

    buildings = new ArrayList<>();
    buildings.add(new Building(ResourceType.LUMBER, ResourceType.METAL, 9, 17));
    buildings.add(new Building(ResourceType.METAL, ResourceType.COAL,9, 17));
    buildings.add(new Building(ResourceType.COAL, ResourceType.NONE,9, 17));

    buildings.get(0).employ(units.get(0));
    buildings.get(1).employ(units.get(1));
    buildings.get(1).employ(units.get(2));
    buildings.get(2).employ(units.get(3));
    buildings.get(2).employ(units.get(4));
    buildings.get(1).employ(units.get(5));
    buildings.get(1).employ(units.get(6));
    buildings.get(1).employ(units.get(7));
    buildings.get(1).employ(units.get(8));
    buildings.get(1).employ(units.get(9));
    buildings.get(1).employ(units.get(10));
    buildings.get(1).employ(units.get(11));

    clock = new Clock(7, 0);

    minuteTimer = 1;

    engine = new Engine(this);
    this.render();
  }

  public void update() {
    if (minuteTimer == MINUTE_INTERVAL / FRAME_INTERVAL) {
      clock.timeStep();
      minuteTimer = 0;

      for (Unit unit : units) {
        switch (unit.getState()) {
          case HOME:
            if (unit.getOccupation() == UnitOccupation.EMPLOYED) {
              if (clock.getHours() >= unit.getBuilding().getBeginsWorkAt() - unit.getTimeOnWay()
                  && clock.getHours()
                  < unit.getBuilding().getEndsWorkAt() - unit.getTimeOnWay() - 3) {
                unit.setState(UnitState.ON_WAY);
              }
            }
            break;

          case ON_WAY:
            if (clock.getHours() == unit.getBuilding().getBeginsWorkAt()) {
              unit.setState(UnitState.WORKING);
            }
            if (clock.getHours() == unit.getBuilding().getEndsWorkAt() + unit.getTimeOnWay()) {
              unit.setState(UnitState.HOME);
            }
            break;

          case WORKING:
            if (clock.getHours() == unit.getBuilding().getEndsWorkAt()) {
              unit.setState(UnitState.ON_WAY);
            }
            break;
        }
      }

      for (Building building : buildings) {
        if (clock.getMinutes() == 0 && clock.getHours() > building.getBeginsWorkAt() && clock.getHours() <= building.getEndsWorkAt()) {
          if (building.getConsumedResource() != ResourceType.NONE) {
            Integer tmp = resources.get(building.getConsumedResource());
            tmp -= building.consumeResource();
            resources.put(building.getConsumedResource(), tmp);
          }
        }

        //Produce resources at the end of the day
        if (clock.getHours() == building.getEndsWorkAt() && clock.getMinutes() == 0) {
          Integer tmp = resources.get(building.getProducedResource());
          tmp += building.produceResources();
          resources.put(building.getProducedResource(), tmp);
        }
      }
    }

    minuteTimer++;
  }

  public void render() {
//    if (clock.getMinutes() != 0) {
//      if (clock.getMinutes() % 10 == 0) {
//        System.out.println();
//      }
//    }
//    System.out.print(clock +  " ");
    engine.render();
  }

  public Clock getClock() {
    return this.clock;
  }

  public List<Unit> getUnits() {
    return this.units;
  }

  public void addUnit(Unit unit) {
    this.units.add(unit);
  }

  public List<Building> getBuildings() {
    return this.buildings;
  }

  public void addBuilding(Building building) {
    this.buildings.add(building);
  }

  public int getFrameInterval() {
    return this.FRAME_INTERVAL;
  }

  public Map<ResourceType, Integer> getResources() {
    return this.resources;
  }
}
