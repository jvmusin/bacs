package istu.bacs.externalapi;

import istu.bacs.domain.Problem;

import java.util.Comparator;

public class NumberHeadComparator implements Comparator<Problem> {

    private static final NumberHeadComparator instance = new NumberHeadComparator();
    public static NumberHeadComparator getInstance() {
        return instance;
    }

    private NumberHeadComparator() {}

    @Override
    public int compare(Problem o1, Problem o2) {
        String id1 = o1.getProblemId();
        String id2 = o2.getProblemId();

        String resource1 = ExternalApiHelper.extractResource(id1);
        String resource2 = ExternalApiHelper.extractResource(id2);

        if (!resource1.equals(resource2))
            return id1.compareTo(id2);

        int number1 = extractHeadNumber(ExternalApiHelper.removeResource(id1));
        int number2 = extractHeadNumber(ExternalApiHelper.removeResource(id2));
        if (number1 != number2)
            return Integer.compare(number1, number2);

        return id1.compareTo(id2);
    }

    private int extractHeadNumber(String name) {
        int number = 0;
        for (int i = 0; i < name.length() && Character.isDigit(name.charAt(i)); i++)
            number = number * 10 + name.charAt(i) - '0';
        return number;
    }
}