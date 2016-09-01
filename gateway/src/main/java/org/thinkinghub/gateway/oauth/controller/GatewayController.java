package org.thinkinghub.gateway.oauth.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.bo.Customer;

@RestController
public class GatewayController {
    @RequestMapping(value = "/controllers/gateway", method = RequestMethod.GET)
    ResponseEntity<String> route(){


        return new ResponseEntity("{}", HttpStatus.OK);
    }
}
