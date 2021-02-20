package org.mychat.backend.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.mychat.backend.users.User;
import org.mychat.backend.users.UserService;


@Component
public class UserLoggedInService {

    private UserService userService;

    private UpgradUserDetailsService userDetailsService;

    @Autowired
    public UserLoggedInService(UserService userService) {
        this.userService = userService;
    }


    public User getLoggedInUser() {

        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=null;
        if(principal instanceof UserDetails){
            username=((UserDetails)principal).getUsername();
        }else
        username=principal.toString();
            return userService.findByUserName(username);

    }


}
