public class Unit {
  private String name;
  private UnitOccupation occupation;
  private UnitState state;
  private UnitState prevState;
  private Building building;
  private int timeOnWay;

  public Unit(String name, int timeOnWay) {
    this.name = name;
    this.state = UnitState.HOME;
    this.prevState = state;
    this.timeOnWay = timeOnWay;
  }

  public String getName() {
    return this.name;
  }

  public UnitOccupation getOccupation() {
    return this.occupation;
  }

  public void setOccupation(UnitOccupation occupation) {
    this.occupation = occupation;
  }

  public UnitState getState() {
    return this.state;
  }

  public void setState(UnitState state) {
    this.prevState = this.state;
    this.state = state;
  }

  public UnitState getPrevState() {
    return this.prevState;
  }

  public Building getBuilding() {
    return this.building;
  }

  public void setBuilding(Building building) {
    this.building = building;
  }

  public int getTimeOnWay() {
    return timeOnWay;
  }
}
