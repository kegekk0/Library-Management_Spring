package service;

import dto.BmiResponse;
import org.springframework.stereotype.Service;

@Service
public class BmiCalculatorService {

    public BmiResponse calculateBmi(double weight, double height) {
        double bmiValue = weight / Math.pow(height / 100, 2);
        int bmi = (int) Math.round(bmiValue);

        String type = determineBmiType(bmi);

        return new BmiResponse(weight, height, bmi, type);
    }

    private String determineBmiType(int bmi) {
        if (bmi < 18) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}