package com.example.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.Student;

import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class ProcessingData {

    public static void preprocess(List<Student> studentsInfo) {
        handleMissing(studentsInfo);
        normalize(studentsInfo);
    }

    private static void handleMissing(List<Student> studentsInfo) {
        double averageHoursStudied = averageValue(studentsInfo, Student::getHoursStudied);
        double averagePreviousScope = averageValue(studentsInfo, Student::getPreviousScores);
        double averageSleepHours = averageValue(studentsInfo, Student::getSleepHours);
        double averageSamplePapers = averageValue(studentsInfo, Student::getSampleQuestionPapersPracticed);
        double averagePerformanceIndex = averageValue(studentsInfo, Student::getPerformanceIndex);
    
        studentsInfo.forEach(student -> {
            if (student.getHoursStudied() == 0) {
                student.setHoursStudied(averageHoursStudied);
            }
            if (student.getPreviousScores() == 0) {
                student.setPreviousScores(averagePreviousScope);
            }
            if (student.getSleepHours() == 0) {
                student.setSleepHours(averageSleepHours);
            }
            if (student.getSampleQuestionPapersPracticed() == 0) {
                student.setSampleQuestionPapersPracticed(averageSamplePapers);
            }
            if (student.getPerformanceIndex() == 0) {
                student.setPerformanceIndex(averagePerformanceIndex);
            }
        });
    }

    private static void normalize(List<Student> studentsInfo) {
        List<Double> minMaxHoursStudied = minMaxValue(studentsInfo, Student::getHoursStudied);
        List<Double> minMaxPreviousScope = minMaxValue(studentsInfo, Student::getPreviousScores);
        List<Double> minMaxSleepHours = minMaxValue(studentsInfo, Student::getSleepHours);
        List<Double> minMaxSamplePapers = minMaxValue(studentsInfo, Student::getSampleQuestionPapersPracticed);
        List<Double> minMaxPerformanceIndex = minMaxValue(studentsInfo, Student::getPerformanceIndex);
    
        studentsInfo.forEach(student -> {
            if (minMaxHoursStudied.get(1) - minMaxHoursStudied.get(0) != 0.0) {
                student.setHoursStudied(normalizeValue(student.getHoursStudied(), minMaxHoursStudied));
            }
            if (minMaxPreviousScope.get(1) - minMaxPreviousScope.get(0) != 0.0) {
                student.setPreviousScores(normalizeValue(student.getPreviousScores(), minMaxPreviousScope));
            }
            if (minMaxSleepHours.get(1) - minMaxSleepHours.get(0) != 0.0) {
                student.setSleepHours(normalizeValue(student.getSleepHours(), minMaxSleepHours));
            }
            if (minMaxSamplePapers.get(1) - minMaxSamplePapers.get(0) != 0.0) {
                student.setSampleQuestionPapersPracticed(normalizeValue(student.getSampleQuestionPapersPracticed(), minMaxSamplePapers));
            }
            if (minMaxPerformanceIndex.get(1) - minMaxPerformanceIndex.get(0) != 0.0) {
                student.setPerformanceIndex(normalizeValue(student.getPerformanceIndex(), minMaxPerformanceIndex));
            }
        });
    }
    
    private static double normalizeValue(double value, List<Double> minMax) {
        return (value - minMax.get(0)) / (minMax.get(1) - minMax.get(0));
    }

    private static List<Double> minMaxValue(List<? extends Student> studentsInfo, ToDoubleFunction<? super Student> func){
        List<Double> data = studentsInfo.stream().mapToDouble(func).boxed().collect(Collectors.toList());
        return Arrays.asList(Collections.min(data), Collections.max(data));
    }
    

    private static double averageValue(List<? extends Student> studentsInfo, ToDoubleFunction<? super Student> func){
        return studentsInfo.stream().mapToDouble(func).average().orElse(0);
    }
}
