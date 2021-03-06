package com.ptak.OrganizerEureka.API;

import com.ptak.OrganizerEureka.model.AuthenticationRequest;
import com.ptak.OrganizerEureka.model.AuthenticatonResponse;
import com.ptak.OrganizerEureka.services.MyUserDetailsService;
import com.ptak.OrganizerEureka.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("security")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity test(){
        return  ResponseEntity.ok("Gitara");
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticatonResponse(jwt));
    }
}
