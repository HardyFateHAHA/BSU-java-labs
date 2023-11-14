import java.io.*;
import java.text.*;

public class Main {
    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            int x = getNum(br);
            int k = getNum(br);
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMaximumFractionDigits(k);

            ApproximateSin approximateSin = new ApproximateSin();
            double approximateValue = approximateSin.getTemp(x, k);

           // StandardSin standardSin = new StandardSin();
            double standardValue = Math.sin(x);/*standardSin.getValue(x);*/

            System.out.println("Приближенное " + formatter.format(approximateValue));
            System.out.println("Стандартное  " + formatter.format(standardValue));
        } catch (IOException e) {
            System.out.println("Ошибка чтения с клавиатуры");
        }
    }

    private static int getNum(BufferedReader br) throws IOException {
        int number = 0;
        boolean flag = false;

        while (!flag) {
            try {
                String line = br.readLine();
                number = Integer.parseInt(line);
                flag = true;
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }
        }

        return number;
    }
}

class ApproximateSin {
    private static double getNext(double last, double x, int n) {
        last *= -1.0 * x * x / n / (n-1);
        return last;
    }

    public static double getTemp(int x_real, int k) {
        double x = x_real % (Math.PI * 2);
        double last = x;
        double sum = x;
        double eps = Math.pow(10, -k - 1);
        int n = 3;

        while (Math.abs(last) > eps) {
            last = getNext(last, x, n);
            sum += last;
            n += 2;
        }

        return sum;
    }
}
/*
class StandardSin {
    public static double getValue(int x) {
        return Math.sin(x);
    }
}*/