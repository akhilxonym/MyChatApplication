package org.mychat.backend.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.mychat.backend.auth.models.LoginRequest;
import org.mychat.backend.auth.models.LoginResponse;
import org.mychat.backend.config.security.TokenProvider;
import org.mychat.backend.exception.AppException;
import org.mychat.backend.service.repository.AuthRepository;
import org.mychat.backend.users.User;
import org.mychat.backend.users.UserService;

import static org.mychat.backend.exception.MyResponseStatusException.asBadRequest;

import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
	public MongoTemplate mongoTemplate;

    private TokenProvider tokenProvider;

    private UserService userService;

    @Autowired
    private AuthRepository authRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
       
        try {

            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),
                            loginRequest.getPassword()
                    )
            );


            if(userService.isApprovedUser( loginRequest.getUserName()) == false){
                throw new AppException("User Not Approved");
            }
                                
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = tokenProvider.generateToken(authentication);
            System.out.println("TOKEN ########"+token);

            User user=authRepository.findByUserName("user", loginRequest.getUserName());
            LoginResponse result=null;
            if(user!=null)
            {
                result= new LoginResponse(loginRequest.getUserName()+"||"+user.getId()+"||"+token, "Success", "");
            }
            
            return new ResponseEntity<>(result,HttpStatus.ACCEPTED);


        } catch (AppException e) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, e.getMessage(), e);
        }catch (AuthenticationException e) {
            e.printStackTrace();
            log.info("AuthenticationException" + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Bad credentials", e);
        }

    }


}
