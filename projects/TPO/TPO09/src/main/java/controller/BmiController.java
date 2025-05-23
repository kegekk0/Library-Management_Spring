package pl.edu.pja.tpo09.controller;

import dto.BmiResponse;
import service.BmiCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/BMI")
public class BmiController {

    @Autowired
    private BmiCalculatorService bmiCalculatorService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> calculateBmi(
            @RequestParam double weight,
            @RequestParam double height,
            @RequestHeader(value = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {

        if (weight <= 0 || height <= 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("reason", "invalid data, weight and height parameters must be positive numbers");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

        BmiResponse response = bmiCalculatorService.calculateBmi(weight, height);

        if (acceptHeader.equals(MediaType.TEXT_PLAIN_VALUE)) {
            return ResponseEntity.ok(String.format("%.2f", response.getBmi()));
        }

        return ResponseEntity.ok(response);
    }
}