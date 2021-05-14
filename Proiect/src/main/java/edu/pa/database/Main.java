package edu.pa.database;

import edu.pa.correct.Address;
import edu.pa.database.finaladdress.FinalAddress;
import edu.pa.database.initialization.DatabaseInitialization;
import edu.pa.database.repository.AddressRepository;
import inputparsing.InputParse;


public class Main {
    public static void main(String[] args) {
        Address address = new Address();
        address.setCountry("Romania");
        address.setCounty("Satu Mare");
        address.setCity("Satu Mare");
        InputParse inputParse = new InputParse(address);
        System.out.println(inputParse.getParsedInput());
        //DatabaseInitialization.databaseInitialization();
    }
}
