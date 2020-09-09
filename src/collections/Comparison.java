package collections;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Comparison {
    private final static String HEADER_FORMAT = "%-13s";
    private final static String FIRST_COLUMN_FORMAT = "%-13d";
    private final static String CELL_ADD_FORMAT = "%-4.0f (%-1.3f) ";
    private final static String CELL_CONTAINS_FORMAT = "%-13.3f";
    private final static String PATH = "./experiment.txt";

    private final static int LOW_BOUND = 0;
    private final static int[] UPPER_BOUND = {10, 100, 1_000, 10_000, 100_000};
    private final static int[] SIZES = {10, 100, 1_000, 10_000, 100_000};

    public static void main(String[] args) throws IOException {
        assert (SIZES.length == UPPER_BOUND.length);

        List<Class<? extends Collection>> collections = Arrays.asList(
                ArrayList.class,
                LinkedList.class,
                TreeSet.class,
                HashSet.class
        );

        File f = new File(PATH);
        if (!f.exists()) {
            f.createNewFile();
        }

        try (FileWriter fw = new FileWriter(f, true)) {
            calculateAndWriteAdditionTime(collections, fw);
            calculateAndWriteContainsOperationTime(collections, fw);
            calculateAndWriteRemoveOperationTime(collections, fw);
        }
    }

    private static void calculateAndWriteRemoveOperationTime(List<Class<? extends Collection>> collections, FileWriter fw) throws IOException {
        System.out.println("Calculating removing time...");
        writeHeaders(collections, "REMOVE operation", fw);
        int iterations = 1000;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZES.length; ++i) {
            int size = SIZES[i];
            int upperBound = UPPER_BOUND[i];
            sb.append(String.format(FIRST_COLUMN_FORMAT, size));

            for (Class<? extends Collection> cl : collections) {
                double time = calculateRemoveOperationTime(cl, size, LOW_BOUND, upperBound, iterations);
                sb.append(String.format(CELL_CONTAINS_FORMAT, time));
            }
            sb.append("\n");
        }
        fw.write(sb.toString());
        System.out.println("Complete");
    }

    private static void calculateAndWriteContainsOperationTime(List<Class<? extends Collection>> collections, FileWriter fw) throws IOException {
        System.out.println("Calculating contains time...");
        writeHeaders(collections, "CONTAINS operation", fw);
        int iterations = 1_0_000;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZES.length; ++i) {
            int size = SIZES[i];
            int upperBound = UPPER_BOUND[i];
            sb.append(String.format(FIRST_COLUMN_FORMAT, size));

            for (Class<? extends Collection> cl : collections) {
                double time = calculateContainsOperationTime(cl, size, upperBound, iterations);
                sb.append(String.format(CELL_CONTAINS_FORMAT, time));
            }
            sb.append("\n");
        }
        fw.write(sb.toString());
        System.out.println("Complete");
    }

    private static void calculateAndWriteAdditionTime(Collection<Class<? extends Collection>> collections, FileWriter fw) throws IOException {
        System.out.println("Calculating addition time...");
        writeHeaders(collections, "ADD operation", fw);
        int iterations = 500;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZES.length; ++i) {
            int upperBound = UPPER_BOUND[i];
            int size = SIZES[i];
            sb.append(String.format(FIRST_COLUMN_FORMAT, size));

            for (Class<? extends Collection> cl : collections) {
                double time = calculateAddOperationTime(cl, size, iterations, upperBound);
                sb.append(String.format(CELL_ADD_FORMAT, time, time / size));
            }
            sb.append("\n");
        }

        fw.write(sb.toString());
        System.out.println("Complete");
    }

    private static void writeHeaders(Collection<Class<? extends Collection>> collections, String operation, FileWriter fw) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(HEADER_FORMAT, ""));
        for (Class cl : collections) {
            sb.append(String.format(HEADER_FORMAT, cl.getSimpleName()));
        }

        sb.append(String.format(HEADER_FORMAT, operation));
        sb.append("\n");
        fw.write(sb.toString());
    }

    private static Collection<Integer> getNewCollectionInstance(Class<? extends Collection> cl) {
        if (cl == ArrayList.class) {
            return new ArrayList<>();

        } else if (cl == LinkedList.class) {
            return new LinkedList<>();

        } else if (cl == TreeSet.class) {
            return new TreeSet<>();

        } else if (cl == HashSet.class) {
            return new HashSet<>();

        } else {
            throw new IllegalArgumentException(cl + " no supported");
        }
    }

    private static double calculateRemoveOperationTime(Class<? extends Collection> cl, int size, int lowBound, int upperBound, int iterations) {
        Random rnd = new Random();

        long time = 0;
        long start, end;
        for (int i = 0; i < iterations; ++i) {
            Collection<Integer> collection = getNewCollectionInstance(cl);
            for (int j = 0; j < size; ++j) {
                collection.add(rnd.nextInt(upperBound - lowBound) + lowBound);
            }

            Integer rndVal = rnd.nextInt(upperBound - lowBound) + lowBound;

            start = System.nanoTime();
            collection.remove(rndVal);
            end = System.nanoTime();
            long d = end - start;
            time += d;
        }

        return 1.0 * time / (iterations * 1000);
    }

    private static double calculateContainsOperationTime(Class<? extends Collection> cl, int size, int upperBound, int iterations) {
        Random rnd = new Random();
        Collection<Integer> collection = getNewCollectionInstance(cl);
        for (int i = 0; i < size; ++i) {
            collection.add(rnd.nextInt(upperBound - Comparison.LOW_BOUND) + Comparison.LOW_BOUND);
        }

        long time = 0;
        long start, end;
        for (int i = 0; i < iterations; ++i) {
            Integer rndVal = rnd.nextInt(upperBound - Comparison.LOW_BOUND) + Comparison.LOW_BOUND;
            start = System.nanoTime();
            collection.contains(rndVal);
            end = System.nanoTime();
            long d = end - start;
            time += d;
        }

        return 1.0 * time / (iterations * 1000);
    }

    private static double calculateAddOperationTime(Class<? extends Collection> cl, int size, int iterations, int upperBound) {
        Random rnd = new Random();
        long start, end;
        Collection<Integer> collection;
        double[] times = new double[iterations];

        for (int i = 0; i < iterations; ++i) {
            collection = getNewCollectionInstance(cl);
            for (int j = 0; j < size; ++j) {
                Integer val = rnd.nextInt(upperBound - Comparison.LOW_BOUND) + Comparison.LOW_BOUND;

                start = System.nanoTime();
                collection.add(val);
                end = System.nanoTime();
                long time = end - start;
                times[i] += time;
            }
            times[i] = times[i] / 1_000;
        }

        return Arrays.stream(times).sum() / times.length;
    }
}
