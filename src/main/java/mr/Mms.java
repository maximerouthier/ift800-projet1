package mr;

import java.util.Arrays;

public class Mms {

  public static final double PROCESSING_TIME_MIN = 1.0;
  public static final double PROCESSING_TIME_MAX = 1000000.0;
  public static final int M_MIN = 1;
  public static final int M_MAX = 100000;
  public static final int N_MIN = 1;
  public static final int N_MAX = 1000000;

  private double[] processingTimes;
  private int m;
  private double optLowerBound;

  public static Mms random() {
    int m = M_MIN + (int) (Math.random() * (M_MAX - M_MIN));
    int n = N_MIN + (int) (Math.random() * (N_MAX - N_MIN));

    double[] processingTimes = new double[n];
    double maxProcessingTime = 0.0;
    double averageProcessingTime = 0.0;
    double weight = 1.0 / m;

    for (int i = 0; i < processingTimes.length; i++) {
      processingTimes[i] =
          PROCESSING_TIME_MIN + Math.random() * (PROCESSING_TIME_MAX - PROCESSING_TIME_MIN);
      if (processingTimes[i] > maxProcessingTime) {
        maxProcessingTime = processingTimes[i];
      }
      averageProcessingTime += weight * processingTimes[i];
    }

    double optLowerBound =
        maxProcessingTime > averageProcessingTime ? maxProcessingTime : averageProcessingTime;

    return new Mms(processingTimes, m, optLowerBound);
  }

  public static Mms copyOf(Mms mms) {
    return new Mms(Arrays.copyOf(mms.processingTimes, mms.processingTimes.length), mms.m,
        mms.optLowerBound);
  }

  public Mms(double[] processingTimes, int m, double optLowerBound) {
    this.processingTimes = processingTimes;
    this.m = m;
    this.optLowerBound = optLowerBound;
  }

  public double[] getProcessingTimes() {
    return processingTimes;
  }

  public int getM() {
    return m;
  }

  public double getOptLowerBound() {
    return optLowerBound;
  }

  @Override
  public String toString() {
    return "n = " + processingTimes.length + ", m = " + m + ", OPT lower bound = "
        + (long) optLowerBound;
  }
}
