package edu.pa.correct;

import edu.pa.correct.Address;
import edu.pa.database.finaladdress.FinalAddress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProjectApplicationTests {


    //Empty address
    @Test
    void correctAddress1(){
        Address address = new Address();
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCountry("Romania");
        answerAddress.setCounty("Bucuresti");
        answerAddress.setCity("Sector 1");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }

    //only City
    @Test
    void correctAddress2(){
        Address address = new Address();
        address.setCity("Tecuci");
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCounty("Galati");
        answerAddress.setCountry("Romania");
        answerAddress.setCity("Tecuci");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }

    //only County
    @Test
    void correctAddress3(){
        Address address = new Address();
        address.setCounty("Galati");
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCounty("Galati");
        answerAddress.setCountry("Romania");
        answerAddress.setCity("Galati");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }


    //only Country
    @Test
    void correctAddress4(){
        Address address = new Address();
        address.setCountry("Romania");
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCountry("Romania");
        answerAddress.setCounty("Bucuresti");
        answerAddress.setCity("Sector 1");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }

    //Correct address
    @Test
    void correctAddress5(){
        Address address = new Address();
        address.setCountry("Romania");
        address.setCounty("Galati");
        address.setCity("Tecuci");
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCountry("Romania");
        answerAddress.setCounty("Galati");
        answerAddress.setCity("Tecuci");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }


    //Wrong county
    @Test
    void correctAddress6(){
        Address address = new Address();
        address.setCountry("Romania");
        address.setCounty("Iasi");
        address.setCity("Tecuci");
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCountry("Romania");
        answerAddress.setCounty("Galati");
        answerAddress.setCity("Tecuci");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }

    //Wrong country, only city
    @Test
    void correctAddress7(){
        Address address = new Address();
        address.setCountry("France");
        address.setCity("Tecuci");
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCountry("Romania");
        answerAddress.setCounty("Galati");
        answerAddress.setCity("Tecuci");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }

    //Different country
    @Test
    void correctAddress8(){
        Address address = new Address();
        address.setCounty("Auvergne-Rhone-Alpes");
        FinalAddress finalAddress = new FinalAddress();

        Address answerAddress = new Address();
        answerAddress.setCountry("France");
        answerAddress.setCounty("Auvergne-Rhone-Alpes");
        answerAddress.setCity("Lyon");
        assertTrue(answerAddress.equalAddress(finalAddress.correctAddress(address)));
    }

}
