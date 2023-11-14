import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод размерности матрицы
        System.out.print("Введите количество строк матрицы: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов матрицы: ");
        int cols = scanner.nextInt();

        // Создание и заполнение матрицы
        double[][] matrix = new double[rows][cols];
        System.out.println("Введите элементы матрицы:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }

        // Задача 1: Найти номера столбцов с монотонными последовательностями
        findMonotonicColumns(matrix);

        // Задача 2: Проверить неравенства m(A,1) <= m(A,2) <= ... <= m(A,m)
        if (checkMinMaxColumns(matrix)) {
            System.out.println("Неравенства m(A,1) <= m(A,2) <= ... <= m(A,m) выполняются.");
        } else {
            System.out.println("Неравенства m(A,1) <= m(A,2) <= ... <= m(A,m) не выполняются.");
        }

        // Задача 3: Умножение строк первой матрицы на максимальные значения второй матрицы
        double[][] resultMatrix = multiplyMatrixByMaxValues(matrix);

        // Вывод результата
        System.out.println("Результат умножения матрицы на максимальные значения:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }

    // Задача 1: Найти номера столбцов с монотонными последовательностями
    private static void findMonotonicColumns(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        System.out.println("Номера столбцов с монотонными последовательностями:");
        for (int j = 0; j < cols; j++) {
            boolean isIncreasing = true;
            boolean isDecreasing = true;

            for (int i = 1; i < rows; i++) {
                if (matrix[i][j] > matrix[i - 1][j]) {
                    isDecreasing = false;
                } else if (matrix[i][j] < matrix[i - 1][j]) {
                    isIncreasing = false;
                }
            }

            if (isIncreasing || isDecreasing) {
                System.out.print(j + " ");
            }
        }
        System.out.println();
    }

    // Задача 2: Проверить неравенства m(A,1) <= m(A,2) <= ... <= m(A,m)
    private static boolean checkMinMaxColumns(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[] minColumnIndices = new int[rows];

        for (int i = 0; i < rows; i++) {
            double min = matrix[i][0];
            int minIndex = 0;

            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                    minIndex = j;
                }
            }

            minColumnIndices[i] = minIndex;
        }

        for (int i = 1; i < rows; i++) {
        //    System.out.print(minColumnIndices[i]);
            if (minColumnIndices[i] < minColumnIndices[i - 1]) {
                return false;
            }
        }

        return true;
    }

    // Задача 3: Умножение строк первой матрицы на максимальные значения второй матрицы
    private static double[][] multiplyMatrixByMaxValues(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] resultMatrix = new double[rows][cols];

        double[] maxValues = new double[rows];

        for (int i = 0; i < rows; i++) {
            double max = matrix[i][0];

            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }

            maxValues[i] = max;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][j] = matrix[i][j] * maxValues[i];
            }
        }

        return resultMatrix;
    }
}