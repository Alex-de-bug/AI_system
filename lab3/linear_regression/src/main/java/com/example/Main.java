package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.utils.CSVParser;
import com.example.utils.DataDivider;
import com.example.utils.JChartStat;
import com.example.utils.ModelTeacher;
import com.example.utils.ProcessingData;


public class Main 
{
    public static void main( String[] args )
    {
        List<Student> studentsInfo = new ArrayList<>();
        CSVParser.parseStudentFile(studentsInfo);
        JChartStat.visualizeStatistics(studentsInfo);

        ProcessingData.preprocess(studentsInfo);

        List<List<Student>> divideInfo = DataDivider.divide(studentsInfo, 0.8);

        ModelTeacher modelTeacher = new ModelTeacher();

        // Модель 1
        String[] model1Features = {"hoursStudied", "previousScores", "extracurricularActivities"};
        modelTeacher.teach(divideInfo.get(0), divideInfo.get(1), model1Features);

        // Модель 2
        String[] model2Features = {"previousScores", "extracurricularActivities", "sampleQuestionPapersPracticed", "performance"};
        modelTeacher.teach(divideInfo.get(0), divideInfo.get(1), model2Features);

        // Модель 3
        String[] model3Features = {"hoursStudied", "previousScores", "extracurricularActivities", "sleepHours", "sampleQuestionPapersPracticed"};
        modelTeacher.teach(divideInfo.get(0), divideInfo.get(1), model3Features);
    }
}
