package com.example;

import lombok.Data;

@Data
public class Patient {
    private double regnancies;
    private double glucose;
    private double bloodPressure;
    private double skinThickness;
    private double insulin;
    private double bmi;
    private double pedigree;
    private double age;
    private double outcome;

    public Patient(double regnancies, double glucose, double bloodPressure, double skinThickness, double insulin,
            double bmi, double pedigree, double age, double outcome) {
                this.regnancies = regnancies;
                this.glucose = glucose;
                this.bloodPressure = bloodPressure;
                this.skinThickness = skinThickness;
                this.insulin = insulin;
                this.bmi = bmi;
                this.pedigree = pedigree;
                this.age = age;
                this.outcome = outcome;
    }
}
