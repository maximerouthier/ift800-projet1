package mr;

import java.util.List;

public class Utils {

  public static double[] toPrimitive(List<Double> list) {
    double[] array = new double[list.size()];
    for (int i = 0; i < array.length; i++) {
      array[i] = list.get(i);
    }
    return array;
  }
}
