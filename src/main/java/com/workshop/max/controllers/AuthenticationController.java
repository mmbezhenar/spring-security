package com.workshop.max.controllers;

import com.workshop.max.config.JwtUtils;
import com.workshop.max.dao.UserDao;
import com.workshop.max.dto.AuthenticationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
     AuthenticationManager authenticationManager;
     UserDao userDao;
     JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public String authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        final UserDetails user = userDao.findUserByEmail(request.email());
        if(user != null){
            return jwtUtils.generateToken(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error has been occurred");
    }
}
