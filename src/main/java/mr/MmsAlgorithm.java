package mr;

public abstract class MmsAlgorithm extends Algorithm {

  protected final Mms mms;
  protected double[][] schedule;

  public MmsAlgorithm(Mms mms) {
    this.mms = mms;
  }

  public Mms getMms() {
    return mms;
  }

  public double[][] getSchedule() {
    return schedule;
  }
}
