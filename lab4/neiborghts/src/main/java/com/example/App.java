package com.example;

import java.util.*;

public class App 
{
    public static void main( String[] args )
    {
        List<Patient> patients = ParserCsv.parsePatientFromCsv("diabetes.csv");
        DataPreprocessor.handleMissingValues(patients);
        DataPreprocessor.scaleData(patients);
        // StatisticsCollector.showStatistics(patients);
        Visualization3D.showVisualization(patients);
    }
}
