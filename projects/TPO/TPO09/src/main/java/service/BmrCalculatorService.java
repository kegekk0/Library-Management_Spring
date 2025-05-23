package service;

import dto.BmrResponse;
import org.springframework.stereotype.Service;

@Service
public class BmrCalculatorService {

    public BmrResponse calculateBmr(String gender, double weight, double height, int age) {
        int bmr = calculateBmrValue(gender, weight, height, age);
        return new BmrResponse(gender.toLowerCase(), weight, height, age, bmr);
    }

    private int calculateBmrValue(String gender, double weight, double height, int age) {
        double bmr;

        if (gender.equalsIgnoreCase("man")) {
            bmr = calculateMaleBmr(weight, height, age);
        } else {
            bmr = calculateFemaleBmr(weight, height, age);
        }

        return (int) Math.round(bmr);
    }

    private double calculateMaleBmr(double weight, double height, int age) {
        return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
    }

    private double calculateFemaleBmr(double weight, double height, int age) {
        return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
    }
}