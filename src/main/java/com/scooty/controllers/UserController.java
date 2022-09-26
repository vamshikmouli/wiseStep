package com.scooty.controllers;

import com.scooty.exceptions.UserAlreadyExistsException;
import com.scooty.models.User;
import com.scooty.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    UserService userService = new UserService();
    @RequestMapping(value = "/add/user", method = RequestMethod.POST)
    public ResponseEntity addUser(final Long userId, final String userName) throws UserAlreadyExistsException {
        userService.addUser(new User(userId, userName));
        return ResponseEntity.ok("");
    }
}
