public class SimulationTest {

  public static void main(String[] args) throws InterruptedException {
    SimulationGame game = new SimulationGame();
    game.init();

    boolean running = true;
    int tmp = 0;
    while (running) {
      game.update();
      game.render();
//      for (Building building : buildings) {
//        System.out.print(getType(building.type) + ": "
//            + building.resource + " resource ");
//      }
//      System.out.println();
//      for (Worker worker : workers) {
//        String name = worker.name;
//        String state = getState(worker.state);
//        String target = worker.state == WorkerState.HOME ?
//            "" : worker.prevState == WorkerState.WORKING ?
//            "home" : worker.building == null ?
//            "walk" : getType(worker.building.type);
//
//        System.out.println(name + " is " + state + " " + target);
//      }
//      System.out.println();
//
//      for (Worker worker : workers) {
//        switch (worker.state) {
//          case HOME:
//            if (objective) {
//              running = false;
//            } else {
//              worker.state = WorkerState.ON_WAY;
//            }
//            worker.prevState = WorkerState.HOME;
//            break;
//          case ON_WAY:
//            if (worker.prevState == WorkerState.WORKING) {
//              worker.state = WorkerState.HOME;
//            } else {
//              worker.state = WorkerState.WORKING;
//            }
//            worker.prevState = WorkerState.ON_WAY;
//            break;
//          case WORKING:
//            worker.building.resource += 5;
//            if (objective) {
//              worker.prevState = WorkerState.WORKING;
//            }
//        }
//      }
//
//      for (Building building : buildings) {
//        if (building.resource == 15) {
//          building.employee.state = WorkerState.ON_WAY;
//          building.employee.prevState = WorkerState.WORKING;
//          objective = true;
//        } else {
//          objective = false;
//        }
//      }

      Thread.sleep(game.getFrameInterval());
    }
  }

  public static String getState(UnitState state) {
    switch (state) {
      case HOME:
        return "at home";
      case ON_WAY:
        return "on the way to";
      case WORKING:
        return "working in";
      default:
        return "";
    }
  }

  public static String getType(ResourceType type) {
    switch (type) {
      case LUMBER:
        return "lumber mill";
      case METAL:
        return "metal foundry";
      default:
        return "";
    }
  }
}
