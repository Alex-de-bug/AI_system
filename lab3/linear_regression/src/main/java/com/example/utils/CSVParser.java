package com.example.utils;
import com.example.Student;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVParser {

    public static void parseStudentFile(List<? super Student> sdList) {
        String csvFile = "Student_Performance.csv";
        
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            reader.skip(1);
            String[] record;
            while ((record = reader.readNext()) != null) {
                Student studentTmp = new Student(
                    Double.parseDouble(record[0]), 
                    Double.parseDouble(record[1]), 
                    (record[2].equalsIgnoreCase("Yes") ? 1.0 : 0.0),
                    Double.parseDouble(record[3]),
                    Double.parseDouble(record[4]),
                    Double.parseDouble(record[5]));
                sdList.add(studentTmp);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
    
}
