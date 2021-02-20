package org.mychat.backend.users;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.mychat.backend.config.security.UserLoggedInService;
import org.mychat.backend.exception.ForbiddenException;
import org.mychat.backend.users.credentials.ChangePasswordRequest;
import org.mychat.backend.users.credentials.ChangePasswordService;
import org.mychat.backend.users.models.AccountStatus;
import org.mychat.backend.users.models.UpdateUserDetailRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    UserLoggedInService userLoggedInService;


    @Autowired
    ChangePasswordService changePasswordService;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    // @PreAuthorize("hasRole('GOVERNMENT_AUTHORITY')")
    // @GetMapping
    // public List<User> listUsers() {

    //     return userService.findAll();
    // }


    @GetMapping(value = "/details")
    public User getMyDetails() {

        return userLoggedInService.getLoggedInUser();
    }


}
