import java.util.*;
import java.util.stream.IntStream;

public class QuickSort {

    enum PartitioningStrategy {
        FIRST_ELEMENT, MIDDLE_ELEMENT, LAST_ELEMENT, MEDIAN_OF_THREE, MEDIAN_OF_TEN, MEDIAN_OF_ALL
    }

    public static void sort(ArrayList<SaleRecord> data, int low, int high, PartitioningStrategy strategy) {
        if (high - low <= 7) {
            insertionSort(data, low, high); // Use insertion sort for small arrays
            return;
        }

        if (low < high) {
            int pi = partition(data, low, high, strategy);
            sort(data, low, pi - 1, strategy);
            sort(data, pi + 1, high, strategy);
        }
    }

    private static int partition(ArrayList<SaleRecord> data, int low, int high, PartitioningStrategy strategy) {
        SaleRecord pivot;
        switch (strategy) {
            case FIRST_ELEMENT:
                pivot = data.get(low);
                break;
            case MIDDLE_ELEMENT:
                pivot = data.get(low + (high - low) / 2);
                break;
            case LAST_ELEMENT:
                pivot = data.get(high);
                break;
            case MEDIAN_OF_THREE:
                pivot = medianOfThree(data, low, high);
                break;
            case MEDIAN_OF_TEN:
                pivot = medianOfSample(data, low, high, 10);
                break;
            case MEDIAN_OF_ALL:
                pivot = medianOfAll(data, low, high);
                break;
            default:
                throw new IllegalArgumentException("Invalid partitioning strategy");
        }

        // Partitioning process
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            if (data.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(data, i, j);
            }
        }
        return i;
    }

    // Median of three method
    private static SaleRecord medianOfThree(ArrayList<SaleRecord> data, int low, int high) {
        int mid = low + (high - low) / 2;
        return medianOf(data.get(low), data.get(mid), data.get(high));
    }

    // Median of n sampled values
    private static SaleRecord medianOfSample(ArrayList<SaleRecord> data, int low, int high, int n) {
        Random rand = new Random();
        ArrayList<SaleRecord> samples = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            samples.add(data.get(rand.nextInt((high - low) + 1) + low));
        }
        Collections.sort(samples);
        return samples.get(n / 2);
    }

    // Median of all elements
    private static SaleRecord medianOfAll(ArrayList<SaleRecord> data, int low, int high) {
        ArrayList<SaleRecord> copy = new ArrayList<>(data.subList(low, high + 1));
        Collections.sort(copy);
        return copy.get(copy.size() / 2);
    }

    // Utility method to find the median of three SaleRecord objects
    private static SaleRecord medianOf(SaleRecord a, SaleRecord b, SaleRecord c) {
        // Assuming SaleRecord has implemented Comparable
        SaleRecord[] arr = new SaleRecord[]{a, b, c};
        Arrays.sort(arr);
        return arr[1]; // The middle element after sorting
    }
    // Insertion sort method
    private static void insertionSort(ArrayList<SaleRecord> data, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            SaleRecord key = data.get(i);
            int j = i - 1;

            while (j >= low && data.get(j).compareTo(key) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, key);
        }
    }
}