package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.util.List;

public class StatisticsCollector extends ApplicationFrame {

    public StatisticsCollector(String title) {
        super(title);
    }

    public void collectAndShowStatistics(List<Patient> patients) {
        double[] regnancies = patients.stream().mapToDouble(Patient::getRegnancies).toArray();
        double[] glucose = patients.stream().mapToDouble(Patient::getGlucose).toArray();
        double[] bloodPressure = patients.stream().mapToDouble(Patient::getBloodPressure).toArray();
        double[] skinThickness = patients.stream().mapToDouble(Patient::getSkinThickness).toArray();
        double[] insulin = patients.stream().mapToDouble(Patient::getInsulin).toArray();
        double[] bmi = patients.stream().mapToDouble(Patient::getBmi).toArray();
        double[] pedigree = patients.stream().mapToDouble(Patient::getPedigree).toArray();
        double[] age = patients.stream().mapToDouble(Patient::getAge).toArray();

        showHistogram("Pregnancies Distribution", regnancies, 10);
        showHistogram("Glucose Distribution", glucose, 10);
        showHistogram("Blood Pressure Distribution", bloodPressure, 10);
        showHistogram("Skin Thickness Distribution", skinThickness, 10);
        showHistogram("Insulin Distribution", insulin, 10);
        showHistogram("BMI Distribution", bmi, 10);
        showHistogram("Pedigree Distribution", pedigree, 10);
        showHistogram("Age Distribution", age, 10);
    }


    private void showHistogram(String title, double[] data, int bins) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries(title, data, bins);

        JFreeChart histogram = ChartFactory.createHistogram(
                title, "Value", "Frequency", dataset, PlotOrientation.VERTICAL, 
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(histogram);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        
        JFrame frame = new JFrame(title);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void showStatistics(List<Patient> patients) {
        StatisticsCollector statsCollector = new StatisticsCollector("Patient Statistics");
        statsCollector.collectAndShowStatistics(patients);
    }
}
