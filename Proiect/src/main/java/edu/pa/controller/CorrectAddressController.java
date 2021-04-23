package edu.pa.controller;

import edu.pa.correct.CorrectAddress;
import edu.pa.service.CorrectAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class CorrectAddressController {
    private final CorrectAddressService correctAddressService;

    @PostMapping(path="correct")
   public ResponseEntity<?> correct(@RequestBody CorrectAddress correctAddress ){
        String response="";
        try{
            response= correctAddressService.correct(correctAddress);
        }catch (IllegalStateException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
        }
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
