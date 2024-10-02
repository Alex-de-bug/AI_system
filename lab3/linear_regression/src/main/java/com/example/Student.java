package com.example;

import lombok.Data;

@Data
public class Student {
    private double hoursStudied;
    private double previousScores;
    private double extracurricularActivities;
    private double sleepHours;
    private double sampleQuestionPapersPracticed;
    private double performanceIndex;
    private double performance;

    public Student(double hoursStudied, double previousScores, double extracurricularActivities, double sleepHours,
            double sampleQuestionPapersPracticed, double performanceIndex) {
        this.hoursStudied = hoursStudied;
        this.previousScores = previousScores;
        this.extracurricularActivities = extracurricularActivities;
        this.sleepHours = sleepHours;
        this.sampleQuestionPapersPracticed = sampleQuestionPapersPracticed;
        this.performanceIndex = performanceIndex;
        this.performance = hoursStudied * sleepHours;
    }
    
}
