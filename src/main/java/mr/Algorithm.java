package mr;

public abstract class Algorithm implements Runnable {

  protected double ratio;
  protected String failureMessage;
  private long duration;

  public final void run() {
    long start = System.nanoTime();
    compute();
    duration = System.nanoTime() - start;
  }

  public double getRatio() {
    return ratio;
  }

  public String getFailureMessage() {
    return failureMessage;
  }

  public long getDuration() {
    return duration;
  }

  @Override
  public String toString() {
    String str = "duration = " + duration / 1000000 + ", ratio = " + ratio;
    return failureMessage == null ? str : str + ", FAILURE: " + failureMessage;
  }

  protected abstract void compute();
}
