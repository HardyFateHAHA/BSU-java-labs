import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите текст (для завершения ввода введите пустую строку):");
        StringBuilder text = new StringBuilder();
        String inputLine;

        while (true) {
            inputLine = scanner.nextLine();
            if (inputLine.isEmpty()) {
                break;
            }
            text.append(inputLine).append("\n");
        }

        System.out.println("Выберите операцию (0 - удаление символа, 1 - вставка символа):");
        int operation = scanner.nextInt();
        scanner.nextLine(); // Считываем перевод строки после числа

        char symbol;
        if (operation == 1) {
            System.out.println("Введите символ для вставки:");
            symbol = scanner.nextLine().charAt(0);
        } else {
            System.out.println("Введите символ для удаления:");
            symbol = scanner.nextLine().charAt(0);
        }

        System.out.println("Введите позицию для вставки (если операция удаления, этот шаг можно пропустить):");
        int position = scanner.nextInt();
        scanner.close();

        String result = processText(text.toString(), operation, symbol, position);
        System.out.println("Результат:");
        System.out.println(result);
    }

    private static String processText(String text, int operation, char symbol, int position) {
        StringBuilder result = new StringBuilder();
        String[] lines = text.split("\n");

        for (String line : lines) {
            if (operation == 0) {
                // Удаление символа из строки
                result.append(line.replace(String.valueOf(symbol), ""));
            } else if (operation == 1) {
                // Вставка символа после k-го символа
                if (position > 0 && position <= line.length()) {
                    int k = position - 1;
                    result.append(line.substring(0, k + 1)).append(symbol).append(line.substring(k + 1));
                } else {
                    result.append(line);
                }
            }
            result.append("\n");
        }

        return result.toString();
    }
}