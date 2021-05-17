package edu.pa.correct;

import edu.pa.correct.controller.CorrectAddressController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CorrectAddressController.class)
public class EntireProgramTest {

    @Autowired
    private MockMvc mvc;

    String asJsonString(Address address){
       return "{\n" +
                "country: " + "\"" +address.getCountry()+"\",\n"
        +  "county: " + "\"" +address.getCounty()+"\",\n"
        +  "city: " + "\"" +address.getCity()+"\",\n"
        +  "postalCode: " + "\"" +address.getPostalCode()+"\",\n"
        +  "streetAddress: " + "\"" +address.getStreetAddress()+"\"\n"
               + "}";
    }
    @Test
    void correctAddress(){
        Address address = new Address();
        address.setCity("Tecuci");
        address.setCounty("Galati");
        address.setCountry("Romania");

        try {
            mvc.perform(MockMvcRequestBuilders
            .get("/hello.jsf").content(asJsonString(address))
            .accept(MediaType.ALL))
                    .andExpect(System.out::println)
                    .andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
