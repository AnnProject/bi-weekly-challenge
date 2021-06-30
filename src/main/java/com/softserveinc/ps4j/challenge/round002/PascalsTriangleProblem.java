package com.softserveinc.ps4j.challenge.round002;

/**
 * Pascal's triangle is triangular matrix that looks like this:
 * <pre>
 * row 0:        1
 * row 1:      1 2 1
 * row 2:     1 3 3 1
 * row 3:    1 4 6 4 1
 * ...
 * </pre>
 * <p>Each value in a row is a sum of two values from the previous row.
 * <p>Task: Calculate the row with a given index.
 * <p>Input restrictions: {@code row >= 0}
 */
class PascalsTriangleProblem {

    int[] solve(int row) {
        int rowSize = row + 1;
        int[] rowPresentation = new int[rowSize];
        for (int column = 0; column < rowSize; column++) {
            rowPresentation[column] = Math.toIntExact(factorial(row) / (factorial(column) * factorial(row - column)));
        }
        return rowPresentation;
    }

    public long factorial(int n) {
        if (n == 0) {
            return 1;
        }
        if (n <= 2) {
            return n;
        }
        return n * factorial(n - 1);
    }
}
