import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Program10 {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
 public static TreeMap<String, ArrayList<SaleRecord>> readCsv(String filePath) {
        TreeMap<String, ArrayList<SaleRecord>> dataMap = new TreeMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] values = line.split(",");

                // Parsing each field from the CSV line
                LocalDate saleDate = LocalDate.parse(values[0], formatter);
                String salesperson = values[1];
                String customerName = values[2];
                String carMake = values[3];
                String carModel = values[4];
                int carYear = Integer.parseInt(values[5]);
                double salePrice = Double.parseDouble(values[6]);
                double commissionRate = Double.parseDouble(values[7]);
                double commissionEarned = Double.parseDouble(values[8]);

                SaleRecord record = new SaleRecord(saleDate, salesperson, customerName, carMake, 
                        carModel, carYear, salePrice, commissionRate, commissionEarned);

                dataMap.computeIfAbsent(record.getCarMake(), k -> new ArrayList<>()).add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataMap;
    }

    // Implementing QuickSort with different partitioning strategies
    static class QuickSort {

        // Partitioning strategies enumeration for clarity
        enum PartitioningStrategy {
            FIRST_ELEMENT, LAST_ELEMENT, MIDDLE_ELEMENT, RANDOM_ELEMENT, MEDIAN_OF_THREE, CUSTOM
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
                case LAST_ELEMENT:
                    pivot = data.get(high);
                    break;
                case MIDDLE_ELEMENT:
                    pivot = data.get(low + (high - low) / 2);
                    break;
                case RANDOM_ELEMENT:
                    pivot = data.get(low + new Random().nextInt(high - low));
                    break;
                case MEDIAN_OF_THREE:
                    pivot = medianOfThree(data, low, high);
                    break;
                case CUSTOM:
                    // Implement your custom strategy here
                    pivot = customPivot(data, low, high);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid partitioning strategy");
            }

            int i = low - 1;
            for (int j = low; j <= high; j++) {
                if (data.get(j).compareTo(pivot) <= 0) {
                    i++;
                    Collections.swap(data, i, j);
                }
            }
            return i;
        }

        private static SaleRecord medianOfThree(ArrayList<SaleRecord> data, int low, int high) {
            int mid = low + (high - low) / 2;
            if (data.get(low).compareTo(data.get(mid)) > 0) {
                Collections.swap(data, low, mid);
            }
            if (data.get(low).compareTo(data.get(high)) > 0) {
                Collections.swap(data, low, high);
            }
            if (data.get(mid).compareTo(data.get(high)) > 0) {
                Collections.swap(data, mid, high);
            }
            return data.get(mid);
        }

        private static SaleRecord customPivot(ArrayList<SaleRecord> data, int low, int high) {
            // Implement your custom pivot selection logic here
            return data.get(low); // Placeholder
        }

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

    public static void main(String[] args) {
        // Check if the file path is provided as a command-line argument
        if (args.length < 1) {
            System.out.println("Please provide the CSV file path as a command-line argument.");
            return;
        }

        String csvFilePath = args[0];
        TreeMap<String, ArrayList<SaleRecord>> carSalesData = readCsv(csvFilePath);

        // Perform sorting and timing operations
        for (QuickSort.PartitioningStrategy strategy : QuickSort.PartitioningStrategy.values()) {
            System.out.println("Sorting with strategy: " + strategy);
            for (Map.Entry<String, ArrayList<SaleRecord>> entry : carSalesData.entrySet()) {
                ArrayList<SaleRecord> saleRecords = new ArrayList<>(entry.getValue()); // Copy to avoid re-sorting

                long startTime = System.nanoTime();
                QuickSort.sort(saleRecords, 0, saleRecords.size() - 1, strategy);
                long endTime = System.nanoTime();

                long duration = endTime - startTime;
                System.out.println("Sorting time for " + entry.getKey() + " with " + strategy + ": " + duration + " nanoseconds");
            }
        }
    }
}

// SaleRecord class definition goes here
