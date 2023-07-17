package com.example.security.controllers;

import com.example.security.dto.UserRequest;
import com.example.security.models.ResponseObject;
import com.example.security.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/login")
    public String hello() {
        return "Hello";
    }

    @PostMapping(value = "/register")
    ResponseEntity<ResponseObject> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Registered", service.insert(userRequest))
        );
    }
}
