package edu.pa.correct;



import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Scope(value = "session")
@Component(value = "correctController")
@ELBeanName(value = "correctController")
@Join(path="/correct", to="/hello.jsf")
@RestController
public class CorrectAddressController {

    private CorrectAddressServiceImpl correctAddressService;
    public Address address;


    public CorrectAddressController(@Autowired Address address, @Autowired CorrectAddressServiceImpl addressService){
        this.correctAddressService=addressService;
        this.address=address;
    }
    @GetMapping()
    public ResponseEntity<String> correct() {
        String response = "";
        try {
            response = correctAddressService.correct(address);
        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
        }
        this.address.setCorrectAddress(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
