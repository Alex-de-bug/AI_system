package com.example.utils;

import java.lang.reflect.Field;
import java.util.List;

import com.example.Student;
import com.example.math.LinearRegression;

public class ModelTeacher {
    public void teach(List<Student> dataForTrain, List<Student> dataForTest, String[] signs){
        double[][] trainB = divideDataForSigns(dataForTrain, signs);
        double[] trainA = dataForTrain.stream().mapToDouble(Student::getPerformanceIndex).toArray();

        double[][] testB = divideDataForSigns(dataForTest, signs);
        double[] testA = dataForTest.stream().mapToDouble(Student::getPerformanceIndex).toArray();

        // Создаем и обучаем модель линейной регрессии
        LinearRegression model = new LinearRegression();
        model.fit(trainB, trainA);

        // Оцениваем модель
        double mse = model.meanSquaredError(testB, testA);
        double rSquared = model.rSquared(testB, testA);

        // Выводим результаты
        System.out.println("Среднеквадратическая ошибка (MSE): " + String.format("%.4f", mse));
        System.out.println("R^2 коэффициент: " + String.format("%.4f", rSquared));

        // Вывод коэффициентов
        double[] coefficients = model.getCoefficients();
        System.out.println("Coefficients:");
        for (int i = 0; i < coefficients.length; i++) {
            System.out.println("b" + i + ": " + String.format("%.4f", coefficients[i]));
        }
        System.out.println();
    }

    private double[][] divideDataForSigns(List<? extends Student> data, String[] signs){
        int n = data.size();
        int m = signs.length;

        double[][] bCoeff = new double[n][m];

        for(int i = 0; i < n; i++){
            Student student = data.get(i);
            for(int j = 0; j < m; j++){
                try {
                    Field field = Student.class.getDeclaredField(signs[j]);
                    field.setAccessible(true);
                    bCoeff[i][j] = (double) field.get(student);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace(); 
                }
            }
        }

        return bCoeff;
    }

}
