package mr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MmsPtas extends MmsAlgorithm {

  protected final double e;

  public MmsPtas(Mms mms, double e) {
    super(mms);
    this.e = e;
  }

  public double getE() {
    return e;
  }

  @Override
  protected void compute() {
    // implementation not finished
    // todo: determine t by a binary search in the interval [LB, 2LB]
    double t = 1.25 * mms.getOptLowerBound();

    coreAlgorithm(t);
  }

  private void coreAlgorithm(double t) {
    double largeMin = t * e;
    int smallCount = 0;
    for (int i = 0; i < mms.getProcessingTimes().length; i++) {
      if (mms.getProcessingTimes()[i] < largeMin) {
        smallCount++;
      }
    }
    double[] smallProcessingTimes = new double[smallCount];
    double[] largeProcessingTimes = new double[mms.getProcessingTimes().length - smallCount];
    int j = 0;
    int k = 0;
    for (int i = 0; i < mms.getProcessingTimes().length; i++) {
      if (mms.getProcessingTimes()[i] < largeMin) {
        smallProcessingTimes[j] = mms.getProcessingTimes()[i];
        j++;
      } else {
        largeProcessingTimes[k] = mms.getProcessingTimes()[i];
        k++;
      }
    }

    Arrays.sort(largeProcessingTimes);
    double e_plus_one = e + 1.0;
    int kUpperBound = (int) Math.ceil(Math.log(1.0 / e) / Math.log(e_plus_one));
    List<Integer> itemCounts = new ArrayList<>(kUpperBound);
    List<Double> distinctItemSizes = new ArrayList<>(kUpperBound);
    double leftEndpoint = largeMin;
    double rightEndpoint = leftEndpoint * e_plus_one;
    boolean isNewInterval = true;

    for (int i = 0; i < largeProcessingTimes.length; i++) {
      while (largeProcessingTimes[i] >= rightEndpoint) {
        leftEndpoint = rightEndpoint;
        rightEndpoint = leftEndpoint * e_plus_one;
        isNewInterval = true;
      }
      if (isNewInterval) {
        itemCounts.add(1);
        distinctItemSizes.add(leftEndpoint);
        isNewInterval = false;
      } else {
        int lastIndex = itemCounts.size() - 1;
        itemCounts.set(lastIndex, itemCounts.get(lastIndex) + 1);
      }
    }

    dynamicProgrammingAlgorithm(itemCounts, distinctItemSizes, t);

    // implementation not finished
  }

  private void dynamicProgrammingAlgorithm(List<Integer> itemCounts, List<Double> distinctItemSizes,
      double binSize) {
    int k = itemCounts.size();
    int[] itemCountMaxs = new int[k];
    for (int i = 0; i < k; i++) {
      itemCountMaxs[i] =
          Math.min((int) Math.floor(binSize / distinctItemSizes.get(i)), itemCounts.get(i));
    }
    List<int[]> q = new ArrayList<>();
    int[] indices = new int[k];
    computeQRecursively(q, indices, itemCountMaxs, distinctItemSizes, binSize);

    int n = itemCounts.isEmpty() ? 0 : Collections.max(itemCounts);
    if (Math.pow(n, k) > Integer.MAX_VALUE) {
      failureMessage = "n^k (" + n + "^" + k + ") too large";
    } else {
      failureMessage = "n^k (" + n + "^" + k + ") ok, but implementation not finished";
    }

    // implementation not finished
    // todo: compute the dynamic programming table when n^k is not too large
  }

  private void computeQRecursively(List<int[]> q, int[] indices, int[] itemCountMaxs,
      List<Double> distinctItemSizes, double binSize) {
    double sum = 0.0;
    for (int i = 0; i < indices.length; i++) {
      sum += indices[i] * distinctItemSizes.get(i);
    }
    if (sum <= binSize) {
      q.add(indices.clone());
    }

    for (int i = 0; i < indices.length; i++) {
      if (indices[i] < itemCountMaxs[i]) {
        indices[i]++;
        computeQRecursively(q, indices, itemCountMaxs, distinctItemSizes, binSize);
        break;
      }
    }
  }
}
