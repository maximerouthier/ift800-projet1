package mr;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class MmsGreedy43Approximation extends MmsAlgorithm {

  public MmsGreedy43Approximation(Mms mms) {
    super(mms);
  }

  @Override
  protected void compute() {
    Arrays.sort(mms.getProcessingTimes());
    ArrayUtils.reverse(mms.getProcessingTimes());
    MmsAlgorithm mmsGreedy2Approximation = new MmsGreedy2Approximation(mms);
    mmsGreedy2Approximation.run();
    schedule = mmsGreedy2Approximation.getSchedule();
    ratio = mmsGreedy2Approximation.getRatio();
  }
}
