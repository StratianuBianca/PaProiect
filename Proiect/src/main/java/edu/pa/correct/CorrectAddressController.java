package edu.pa.correct;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class CorrectAddressController {
    @Autowired
    private final CorrectAddressService correctAddressService;

    @GetMapping(path = "correct")
    public ResponseEntity<String> correct(@RequestBody CorrectAddress correctAddress) {
        String response = "";
        try {
            response = correctAddressService.correct(correctAddress);
        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}