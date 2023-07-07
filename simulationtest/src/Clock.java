public class Clock {

  private int hours, minutes;

  public Clock(int hours, int minutes) {
    this.hours = hours;
    this.minutes = minutes;
  }

  public void timeStep() {
    if (minutes == 59) {
      hours++;
      minutes = 0;
    } else {
      minutes++;
    }
    if (hours == 24) {
      hours = 0;
    }
  }

  public int getHours() {
    return this.hours;
  }

  public int getMinutes() {
    return this.minutes;
  }

  @Override
  public String toString() {
    return (this.hours < 10 ? "0" + this.hours : this.hours) + ":"
        + (this.minutes < 10 ? "0" + this.minutes : this.minutes);
  }
}
