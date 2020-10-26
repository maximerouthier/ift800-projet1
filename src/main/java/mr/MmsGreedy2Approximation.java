package mr;

import com.google.common.primitives.Doubles;
import edu.stanford.nlp.math.ArrayMath;
import java.util.ArrayList;
import java.util.List;

public class MmsGreedy2Approximation extends MmsAlgorithm {

  public MmsGreedy2Approximation(Mms mms) {
    super(mms);
  }

  @Override
  protected void compute() {
    @SuppressWarnings("unchecked")
    List<Double>[] resizableSchedule = new List[mms.getM()];
    for (int i = 0; i < resizableSchedule.length; i++) {
      resizableSchedule[i] = new ArrayList<>();
    }
    double[] sums = new double[mms.getM()];

    for (int i = 0; i < mms.getProcessingTimes().length; i++) {
      int argmin = ArrayMath.argmin(sums);
      resizableSchedule[argmin].add(mms.getProcessingTimes()[i]);
      sums[argmin] += mms.getProcessingTimes()[i];
    }

    schedule = new double[mms.getM()][];
    for (int i = 0; i < schedule.length; i++) {
      schedule[i] = Utils.toPrimitive(resizableSchedule[i]);
    }
    ratio = Doubles.max(sums) / mms.getOptLowerBound();
  }
}
