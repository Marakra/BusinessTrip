package org.infoshere.service;


public class BMICalculator {
    
    public double bmiCalculation(double weight, double height) {
        long factor = (long) Math.pow(10, 2);
        double bmi = weight / Math.pow(height, 2);
        bmi = bmi * factor;
        long tmp = Math.round(bmi);

        return (double) tmp / factor;
    }
}
