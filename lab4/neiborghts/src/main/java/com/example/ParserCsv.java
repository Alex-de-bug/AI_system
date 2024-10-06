package com.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserCsv {

    public static List<Patient> parsePatientFromCsv(String filename) {
        List<Patient> patients = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] header = reader.readNext();
            
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                double pregnancies = Double.parseDouble(row[0]);
                double glucose = Double.parseDouble(row[1]);
                double bloodPressure = Double.parseDouble(row[2]);
                double skinThickness = Double.parseDouble(row[3]);
                double insulin = Double.parseDouble(row[4]);
                double bmi = Double.parseDouble(row[5]);
                double pedigree = Double.parseDouble(row[6]);
                double age = Double.parseDouble(row[7]);
                double outcome = Double.parseDouble(row[8]);

                Patient patient = new Patient(pregnancies, glucose, bloodPressure, skinThickness, insulin, bmi, pedigree, age, outcome);
                patients.add(patient);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return patients;
    }
}
