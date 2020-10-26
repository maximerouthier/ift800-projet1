package mr;

public class Main {

  public static final int INSTANCE_COUNT = 10;
  public static final double WEIGHT = 1.0 / INSTANCE_COUNT;
  public static final double E1 = 1.0 / 3;
  public static final double E2 = 1.0 / 9;
  public static final double E3 = E2 / 8;

  private Algorithm[] mmsGreedy2Approximations;
  private Algorithm[] mmsGreedy43Approximations;
  private Algorithm[] mmsPtasE1s;
  private Algorithm[] mmsPtasE2s;
  private Algorithm[] mmsPtasE3s;

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    mmsGreedy2Approximations = new Algorithm[INSTANCE_COUNT];
    mmsGreedy43Approximations = new Algorithm[INSTANCE_COUNT];
    mmsPtasE1s = new Algorithm[INSTANCE_COUNT];
    mmsPtasE2s = new Algorithm[INSTANCE_COUNT];
    mmsPtasE3s = new Algorithm[INSTANCE_COUNT];
    createInstances();
    runAlgorithms(mmsGreedy2Approximations, "computing greedy 2-approximations...");
    runAlgorithms(mmsGreedy43Approximations, "computing greedy 4/3-approximations...");
    runAlgorithms(mmsPtasE1s, "computing PTASs... (e = " + E1 + ")");
    runAlgorithms(mmsPtasE2s, "computing PTASs... (e = " + E2 + ")");
    runAlgorithms(mmsPtasE3s, "computing PTASs... (e = " + E3 + ")");
  }

  private void createInstances() {
    System.out.println("creating instances...");
    for (int i = 0; i < INSTANCE_COUNT; i++) {
      Mms mms = Mms.random();
      mmsGreedy2Approximations[i] = new MmsGreedy2Approximation(mms);
      mmsGreedy43Approximations[i] = new MmsGreedy43Approximation(Mms.copyOf(mms));
      mmsPtasE1s[i] = new MmsPtas(Mms.copyOf(mms), E1);
      mmsPtasE2s[i] = new MmsPtas(Mms.copyOf(mms), E2);
      mmsPtasE3s[i] = new MmsPtas(Mms.copyOf(mms), E3);
      System.out.println(i + ": " + mms.toString());
    }
    System.out.println("done\n");
  }

  private void runAlgorithms(Algorithm[] algorithms, String message) {
    System.out.println(message);
    long averageDuration = 0;
    double averageRatio = 0.0;
    for (int i = 0; i < INSTANCE_COUNT; i++) {
      algorithms[i].run();
      averageDuration += WEIGHT * algorithms[i].getDuration();
      averageRatio += WEIGHT * algorithms[i].getRatio();
      System.out.println(i + ": " + algorithms[i].toString());
    }
    System.out.println("done: average duration = " + averageDuration / 1000000
        + ", average ratio = " + averageRatio + "\n");
  }
}
