package edu.pa.correct;

import edu.pa.correct.CorrectAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CorrectAddressService {
    public String correct(CorrectAddress correctAddress){
        return "yes";
    }
}
