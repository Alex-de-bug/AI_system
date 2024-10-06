package com.example;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import java.util.*;

public class DataPreprocessor {

    public static List<Patient> handleMissingValues(List<Patient> patients) {
        double[] pregnancies = extractValues(patients, "pregnancies");
        double[] glucose = extractValues(patients, "glucose");
        double[] bloodPressure = extractValues(patients, "bloodPressure");
        double[] skinThickness = extractValues(patients, "skinThickness");
        double[] insulin = extractValues(patients, "insulin");
        double[] bmi = extractValues(patients, "bmi");
        double[] pedigree = extractValues(patients, "pedigree");
        double[] age = extractValues(patients, "age");

        double pregnanciesMedian = calculateMedian(pregnancies);
        double glucoseMedian = calculateMedian(glucose);
        double bloodPressureMedian = calculateMedian(bloodPressure);
        double skinThicknessMedian = calculateMedian(skinThickness);
        double insulinMedian = calculateMedian(insulin);
        double bmiMedian = calculateMedian(bmi);
        double pedigreeMedian = calculateMedian(pedigree);
        double ageMedian = calculateMedian(age);

        for (Patient patient : patients) {
            if (isMissingValue(patient.getRegnancies())) {
                patient.setRegnancies(pregnanciesMedian);
            }
            if (isMissingValue(patient.getGlucose())) {
                patient.setGlucose(glucoseMedian);
            }
            if (isMissingValue(patient.getBloodPressure())) {
                patient.setBloodPressure(bloodPressureMedian);
            }
            if (isMissingValue(patient.getSkinThickness())) {
                patient.setSkinThickness(skinThicknessMedian);
            }
            if (isMissingValue(patient.getInsulin())) {
                patient.setInsulin(insulinMedian);
            }
            if (isMissingValue(patient.getBmi())) {
                patient.setBmi(bmiMedian);
            }
            if (isMissingValue(patient.getPedigree())) {
                patient.setPedigree(pedigreeMedian);
            }
            if (isMissingValue(patient.getAge())) {
                patient.setAge(ageMedian);
            }
        }

        return patients;
    }

    private static boolean isMissingValue(Double value) {
        return value == null || Double.isNaN(value);
    }

    public static List<Patient> scaleData(List<Patient> patients) {
        double[] pregnancies = extractValues(patients, "pregnancies");
        double[] glucose = extractValues(patients, "glucose");
        double[] bloodPressure = extractValues(patients, "bloodPressure");
        double[] skinThickness = extractValues(patients, "skinThickness");
        double[] insulin = extractValues(patients, "insulin");
        double[] bmi = extractValues(patients, "bmi");
        double[] pedigree = extractValues(patients, "pedigree");
        double[] age = extractValues(patients, "age");

        double pregnanciesMean = calculateMean(pregnancies);
        double glucoseMean = calculateMean(glucose);
        double bloodPressureMean = calculateMean(bloodPressure);
        double skinThicknessMean = calculateMean(skinThickness);
        double insulinMean = calculateMean(insulin);
        double bmiMean = calculateMean(bmi);
        double pedigreeMean = calculateMean(pedigree);
        double ageMean = calculateMean(age);

        double pregnanciesStd = calculateStd(pregnancies);
        double glucoseStd = calculateStd(glucose);
        double bloodPressureStd = calculateStd(bloodPressure);
        double skinThicknessStd = calculateStd(skinThickness);
        double insulinStd = calculateStd(insulin);
        double bmiStd = calculateStd(bmi);
        double pedigreeStd = calculateStd(pedigree);
        double ageStd = calculateStd(age);

        for (Patient patient : patients) {
            patient.setRegnancies((patient.getRegnancies() - pregnanciesMean) / pregnanciesStd);
            patient.setGlucose((patient.getGlucose() - glucoseMean) / glucoseStd);
            patient.setBloodPressure((patient.getBloodPressure() - bloodPressureMean) / bloodPressureStd);
            patient.setSkinThickness((patient.getSkinThickness() - skinThicknessMean) / skinThicknessStd);
            patient.setInsulin((patient.getInsulin() - insulinMean) / insulinStd);
            patient.setBmi((patient.getBmi() - bmiMean) / bmiStd);
            patient.setPedigree((patient.getPedigree() - pedigreeMean) / pedigreeStd);
            patient.setAge((patient.getAge() - ageMean) / ageStd);
        }

        return patients;
    }

    // Вспомогательные методы для обработки массивов данных
    private static double[] extractValues(List<Patient> patients, String field) {
        switch (field) {
            case "pregnancies":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getRegnancies()).orElse(Double.NaN)).toArray();
            case "glucose":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getGlucose()).orElse(Double.NaN)).toArray();
            case "bloodPressure":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getBloodPressure()).orElse(Double.NaN)).toArray();
            case "skinThickness":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getSkinThickness()).orElse(Double.NaN)).toArray();
            case "insulin":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getInsulin()).orElse(Double.NaN)).toArray();
            case "bmi":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getBmi()).orElse(Double.NaN)).toArray();
            case "pedigree":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getPedigree()).orElse(Double.NaN)).toArray();
            case "age":
                return patients.stream().mapToDouble(p -> Optional.ofNullable(p.getAge()).orElse(Double.NaN)).toArray();
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

    // Метод для вычисления медианы
    private static double calculateMedian(double[] values) {
        Median median = new Median();
        return median.evaluate(values);
    }

    // Метод для вычисления среднего значения
    private static double calculateMean(double[] values) {
        return Arrays.stream(values).average().orElse(0);
    }

    // Метод для вычисления стандартного отклонения
    private static double calculateStd(double[] values) {
        double mean = calculateMean(values);
        return Math.sqrt(Arrays.stream(values).map(v -> Math.pow(v - mean, 2)).sum() / values.length);
    }
}

