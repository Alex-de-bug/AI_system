package com.example.utils;
import java.util.*;
import com.example.Student;

public  class DataDivider {
    public static List<List<Student>> divide(List<? super Student> info, double trainRatio) {
        List<Student> forTrain = new ArrayList<>();
        List<Student> forTest = new ArrayList<>();
        
        Random random = new Random();
        
        for (Object obj : info) {
            Student student = (Student) obj; 
            if (random.nextDouble() < trainRatio) {
                forTrain.add(student);
            } else {
                forTest.add(student); 
            }
        }
        
        List<List<Student>> dividedData = new ArrayList<>();
        dividedData.add(forTrain);
        dividedData.add(forTest);
        
        return dividedData;
    }
}
