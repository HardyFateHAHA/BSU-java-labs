import org.junit.jupiter.api.Test;

import java.text.NumberFormat;

import static org.junit.jupiter.api.Assertions.*;

class ApproximateSinTest {

    @Test
    void TestGetTempMetod() {
        ApproximateSin approximateSin = new ApproximateSin();
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(3);
        assertEquals(formatter.format(Math.sin(5)) , formatter.format(ApproximateSin.getTemp(5 , 3)));
    }
    @Test
    void testGetTempBaseCase() {
        double result = ApproximateSin.getTemp(0, 2);
        double expected = 0.0; // Ожидаемый результат
        assertEquals(expected, result, 0.01); // Проверяем, что результат близок к ожидаемому
    }
    @Test
    void testGetTempNegativeX() {
        double result = ApproximateSin.getTemp(-90, 3);
        double expected = Math.sin(-90); // Ожидаемый результат
        assertEquals(expected, result, 0.01); // Проверяем, что результат близок к ожидаемому
    }

    @Test
    void testGetTempLargeK() {
        double result = ApproximateSin.getTemp(30, 10);
        double expected = Math.sin(30); // Ожидаемый результат
        assertEquals(expected, result, 0.0000000001); // Проверяем, что результат близок к ожидаемому
    }
    @Test
    void testGetTempLargeX() {
        double result = ApproximateSin.getTemp(1000, 4);
        double expected = Math.sin(1000); // Ожидаемый результат для эквивалентного угла
        assertEquals(expected, result, 0.0001); // Проверяем, что результат близок к ожидаемому
    }


    @Test
    void testGetTempSmallK() {
        double result = ApproximateSin.getTemp(45, 0);
        double expected = Math.sin(45); // Ожидаемый результат с использованием стандартной функции Math.sin
        assertEquals(expected, result, 0.001); // Проверяем, что результат близок к ожидаемому
    }
}