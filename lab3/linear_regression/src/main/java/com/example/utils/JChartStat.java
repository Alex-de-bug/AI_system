package com.example.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.example.Student;

import java.util.function.ToDoubleFunction;


public class JChartStat extends JFrame {
    public JChartStat(List<? extends Student> studentsInfo) {
        super("Визуализация статистики по датасету");
        JFreeChart jFreeChart = createChart(studentsInfo);
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFreeChart createChart(List<? extends Student> studentsInfo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        addDatasetEntry(dataset, studentsInfo.size(), "Всего студентов", "Count");

        addStatEntries(dataset, studentsInfo, "Hours Studied", Student::getHoursStudied);
        addStatEntries(dataset, studentsInfo, "Previous Scores", Student::getPreviousScores);
        addStatEntries(dataset, studentsInfo, "Sleep Hours", Student::getSleepHours);
        addStatEntries(dataset, studentsInfo, "Sample Papers Practiced", Student::getSampleQuestionPapersPracticed);
        addStatEntries(dataset, studentsInfo, "Performance Index", Student::getPerformanceIndex);

        return ChartFactory.createBarChart(
                "Student Data",
                "Metric",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
    }

    private void addDatasetEntry(DefaultCategoryDataset dataset, double value, String rowKey, String columnKey) {
        dataset.addValue(value, rowKey, columnKey);
    }

    private void addStatEntries(DefaultCategoryDataset dataset, List<? extends Student> studentsInfo, String columnKey, ToDoubleFunction<Student> extractor) {
        List<Double> data = studentsInfo.stream().mapToDouble(extractor).boxed().collect(Collectors.toList());
        addDatasetEntry(dataset, data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0), "Среднее значение", columnKey);
        addDatasetEntry(dataset, calculateStdDev(data), "Отклонение", columnKey);
        addDatasetEntry(dataset, Collections.min(data), "Минимум", columnKey);
        addDatasetEntry(dataset, Collections.max(data), "Максимум", columnKey);
        addDatasetEntry(dataset, calculateQuantile(data, 0.25), "25th Процентиль", columnKey);
        addDatasetEntry(dataset, calculateQuantile(data, 0.5), "Медиана", columnKey);
        addDatasetEntry(dataset, calculateQuantile(data, 0.75), "75th Процентиль", columnKey);
    }

    private double calculateStdDev(List<Double> data) {
        double mean = data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = data.stream().mapToDouble(x -> Math.pow(x - mean, 2)).average().orElse(0.0);
        return Math.sqrt(variance);
    }

    private double calculateQuantile(List<Double> data, double quantile) {
        List<Double> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData);
        int index = (int) Math.ceil(quantile * sortedData.size()) - 1;
        return sortedData.get(index);
    }

    public static void visualizeStatistics(List<? extends Student> studentDataList) {
        JChartStat chart = new JChartStat(studentDataList);
        chart.pack();
        chart.setLocationRelativeTo(null);
        chart.setVisible(true);
    }
}
