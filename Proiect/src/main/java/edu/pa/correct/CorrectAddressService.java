package edu.pa.correct;

import edu.pa.database.finaladdress.FinalAddress;
import org.springframework.stereotype.Service;

@Service
public class CorrectAddressService {
    //face apel la Final address, care o returneaza adresa si va verifica daca e aceeasi sau nu
    public String correct(Address address){
        FinalAddress finalAddress = new FinalAddress();
        Address correctAddress = finalAddress.correctAddress(address);
        //checking

        return "yes";
    }
}
