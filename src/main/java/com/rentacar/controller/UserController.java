package com.rentacar.controller;

import com.rentacar.model.UserDTO;
import com.rentacar.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private ResponseEntity<List<UserDTO>> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PostMapping
    private ResponseEntity<UserDTO> createUser(@Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }
}
