package edu.pa.correct;

import edu.pa.database.finaladdress.FinalAddress;
import org.springframework.stereotype.Service;

@Service
public class CorrectAddressServiceImpl implements CorrectAddressService{
    @Override
    public String correct(Address address) {
        FinalAddress finalAddress = new FinalAddress();
        Address correctAddress = finalAddress.correctAddress(address);
        if(address.equalAddress(correctAddress)) return "Correct Address: \n" + address;
        return "Bad address: \n" + address + "\n" + "Correct Address: \n" + correctAddress;
    }
}
