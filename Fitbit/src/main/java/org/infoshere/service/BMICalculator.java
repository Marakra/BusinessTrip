package org.infoshere.service;

import java.util.Scanner;

public class BMICalculator {
    
    public void bmiCalculation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter weight in kg: ");
        double weight = scanner.nextDouble();
        System.out.println("Enter height in meters: example 1,67");
        double height = scanner.nextDouble();
        double bmi = weight / Math.pow(height, 2);
        System.out.println("BMI: " + bmi);
    }
    
}
