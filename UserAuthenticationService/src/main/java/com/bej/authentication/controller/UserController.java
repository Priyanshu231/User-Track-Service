package com.bej.authentication.controller;

import com.bej.authentication.exception.UserAlreadyExistsException;
import com.bej.authentication.exception.InvalidCredentialsException;
import com.bej.authentication.security.SecurityTokenGenerator;
import com.bej.authentication.service.IUserService;
import com.bej.authentication.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    //Autowired the dependencies for UserService and SecurityTokenGenerator
    private IUserService userService;
    private SecurityTokenGenerator securityTokenGenerator;
    private ResponseEntity responseEntity;
    @Autowired
    public UserController(IUserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException{
        // Write the logic to save a user,
        try {
            responseEntity = new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
        // return 201 status if user is saved else 500 status
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws InvalidCredentialsException
    {
        // Generate the token on login,
        User retrivedUser = userService.getUserByUserIdAndPassword(user.getUserId(), user.getPassword());
        if (retrivedUser == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new InvalidCredentialsException();
        } else {
            String token = securityTokenGenerator.createToken(user);
            // return 200 status if user is saved else 500 status
            responseEntity=new ResponseEntity<>(token,HttpStatus.OK);
        }
        return responseEntity;
    }
}
