package edu.pa.correct.controller;


import edu.pa.correct.Address;
import edu.pa.correct.CorrectAddressServiceImpl;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@Controller
public class CorrectAddressController {

    private CorrectAddressServiceImpl correctAddressService;
    public Address address;
    public CorrectAddressController(@Autowired Address address, @Autowired CorrectAddressServiceImpl addressService){
        this.correctAddressService=addressService;
        this.address=address;
    }
    @RequestMapping(value = "/correctAddress")
    public ModelAndView home() {
        return new ModelAndView("correctAddress");
    }

    @RequestMapping(value = "/correctAddress", method = RequestMethod.POST)
    public ModelAndView correctAdd(Model model,@ModelAttribute("Address") Address address) {
        String response="";
        response= correctAddressService.correct(address);
        this.address.setCorrectAddress(response);
        model.addAttribute("address", response);
        return new ModelAndView("/correct");
    }

}
/*
    @RequestMapping(value = "/correctAddress", method = RequestMethod.GET)
    public ModelAndView correct(){
        return  new ModelAndView("correctAddress");
    }


    @RequestMapping(value = "/correctAddress", method = RequestMethod.POST)
    public ModelAndView correctAddress() {
        return new ModelAndView("correct");
    }
    public CorrectAddressController(@Autowired Address address, @Autowired CorrectAddressServiceImpl addressService) {
        this.correctAddressService = addressService;
        this.address = address;
    }

   // @RequestMapping("/correctAddress")
    /* public ResponseEntity<String> correct() {
         String response = "";
         try {
           //  response = correctAddressService.correct(address);
         } catch (IllegalStateException exception) {
             return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
         }
         //this.address.setCorrectAddress(response);
         return new ResponseEntity<>(response, HttpStatus.OK);

     }*/

    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       address.setCountry(request.getParameter("country"));
        address.setCounty(request.getParameter("county"));
        address.setCity(request.getParameter("city"));
        address.setPostalCode(request.getParameter("postalCode"));
        address.setStreetAddress(request.getParameter("streetAddress"));
        RequestDispatcher req = request.getRequestDispatcher("correct.jsp");
        req.include(request, response);*/
        /* response = "";
        try {
            //  response = correctAddressService.correct(address);
        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
        }
        //this.address.setCorrectAddress(response);
        return new ResponseEntity<>(response, HttpStatus.OK);*/

   // }
//}

