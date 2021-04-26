package edu.pa.correct;

import org.springframework.stereotype.Service;

@Service
public class CorrectAddressService {
    public String correct(Address address){
        return "yes";
    }
}
