package controller;

import dto.BmrResponse;
import service.BmrCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/BMR")
public class BmrController {

    @Autowired
    private BmrCalculatorService bmrCalculatorService;

    @GetMapping(value = "/{gender}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> calculateBmr(
            @PathVariable String gender,
            @RequestParam double weight,
            @RequestParam double height,
            @RequestParam int age,
            @RequestHeader(value = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {

        if (!gender.equalsIgnoreCase("man") && !gender.equalsIgnoreCase("woman")) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("reason", "invalid gender data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

        if (weight <= 0 || height <= 0 || age <= 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("reason", "invalid data, weight, height and age parameters must be positive numbers");
            return ResponseEntity.status(499).headers(headers).build();
        }

        BmrResponse response = bmrCalculatorService.calculateBmr(gender, weight, height, age);

        if (acceptHeader.equals(MediaType.TEXT_PLAIN_VALUE)) {
            return ResponseEntity.ok(response.getBmr() + "kcal");
        }

        return ResponseEntity.ok(response);
    }
}