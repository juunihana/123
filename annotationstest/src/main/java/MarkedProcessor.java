import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MarkedProcessor {

  public boolean checkIfMarked(Object object) {
    Class<?> c = object.getClass();
    if (c.isAnnotationPresent(Marked.class)) {
      return true;
    }
    return false;
  }

}
