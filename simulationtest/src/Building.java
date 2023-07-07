import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Building {

  private final int RESOURCE_BASE_RATE = 20;
  private final int CONSUME_RESOURCE_BASE_RATE = 4;

  private List<Unit> employees;
  private ResourceType producedResource;
  private ResourceType consumedResource;
  private int maxResource;
  private int beginsWorkAt;
  private int endsWorkAt;

  public Building(ResourceType producedResource, ResourceType consumedResource, int beginsWorkAt, int endsWorkAt) {
    this.producedResource = producedResource;
    this.maxResource = new Random().nextInt(200) + 800;
    this.beginsWorkAt = beginsWorkAt;
    this.endsWorkAt = endsWorkAt;
    this.consumedResource = consumedResource;

    employees = new ArrayList<>();
  }

  public void employ(Unit unit) {
    employees.add(unit);
    unit.setBuilding(this);
    unit.setOccupation(UnitOccupation.EMPLOYED);
  }

  public void fire(String name) {
    for (Unit unit : employees) {
      if (unit.getName().equals(name)) {
        unit.setOccupation(UnitOccupation.UNEMPLOYED);
        break;
      }
    }
    removeUnemployed();
  }

  public void fireAll() {
    for (Unit unit : employees) {
      unit.setOccupation(UnitOccupation.UNEMPLOYED);
    }
    removeUnemployed();
  }

  private void removeUnemployed() {
    employees = employees.stream()
        .filter(employee -> employee.getOccupation() != UnitOccupation.UNEMPLOYED)
        .collect(Collectors.toList());
  }

  public ResourceType getProducedResource() {
    return this.producedResource;
  }

  public ResourceType getConsumedResource() {
    return this.consumedResource;
  }

  public int getMaxResource() {
    return this.maxResource;
  }

  public int produceResources() {
    if (maxResource > 0) {
      maxResource -= RESOURCE_BASE_RATE * employees.size() + new Random().nextInt(5);
      return RESOURCE_BASE_RATE * employees.size();
    } else {
      fireAll();
      return 0;
    }
  }

  public int consumeResource() {
    return CONSUME_RESOURCE_BASE_RATE + new Random().nextInt(3);
  }

  public int getBeginsWorkAt() {
    return this.beginsWorkAt;
  }

  public int getEndsWorkAt() {
    return this.endsWorkAt;
  }

  public int getEmployeesNumber() {
    return this.employees.size();
  }
}
