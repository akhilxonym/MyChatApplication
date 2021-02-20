package org.mychat.backend.auth.register;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.mychat.backend.exception.AppException;
import org.mychat.backend.users.User;
import org.mychat.backend.users.UserService;
import org.mychat.backend.users.models.AccountStatus;
import org.mychat.backend.users.roles.UserRole;

@Service
public class RegisterService {

    @Autowired
    private UserService userService;

    @Autowired
    public MongoTemplate mongoTemplate;

    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);

    public User addUser(RegisterRequest registerRequest) {

        User user = new User();
        user.setAddress(registerRequest.getAddress());
        Date date = new Date();
        user.setCreated(date);
        Date localDate = new Date();
        user.setDateOfBirth(localDate);
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        user.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPassword()));
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPinCode(registerRequest.getPinCode());
        user.setUserName(registerRequest.getUserName());
        user.setRoles(userService.getRoleFor(UserRole.USER));
        user.setStatus(AccountStatus.APPROVED);
        if(userService.findByUserName(user.getUserName())==null)
            {   
                userService.saveInDatabase(user);
                user.setId( UUID.randomUUID().toString().replace("-", ""));
                mongoTemplate.insert(user, "user");

            }
        else
            return null;
    //    mongoTemplate.insert(user, "user");
        
/*      User should be validated before registration.
                the username , email and phone number should be unique (i.e should throw AppException if the RegisterRequest has the same username or email or phone number)
                    hint:
                        userService.findByUserName
                        userService.findByEmail
                        userService.findByPhoneNumber

         A new User Object should be created with same details as registerRequest
                password should be encrypted with help of   userService.toEncrypted
                roles should be set with help of  userService.getRoleFor(UserRole.USER)
                status should be set to AccountStatus.APPROVED

        And finally
            Call userService.saveInDatabase to save the new user and return the saved user
*/
        return user;
    }

    public User addDoctor(RegisterRequest user) {


/*      Doctor should be validated before registration.
                the username , email and phone number should be unique (i.e should throw AppException if the RegisterRequest has the same username or email or phone number)
                    hint:
                        userService.findByUserName
                        userService.findByEmail
                        userService.findByPhoneNumber

         A new User Object should be created with same details as registerRequest
                password should be encrypted with help of   userService.toEncrypted
                roles should be set with help of  userService.getRoleFor(UserRole.DOCTOR)
                status should be set to AccountStatus.INITIATED

        And finally
            Call userService.saveInDatabase to save the newly registered doctor and return the saved value
*/
        throw new AppException("Method Not Implemented");
    }


    public User addTester(RegisterRequest user) {


/*      Tester should be validated before registration.
                the username , email and phone number should be unique (i.e should throw AppException if the RegisterRequest has the same username or email or phone number)
                    hint:
                        userService.findByUserName
                        userService.findByEmail
                        userService.findByPhoneNumber

         A new User Object should be created with same details as registerRequest
                password should be encrypted with help of   userService.toEncrypted
                roles should be set with help of  userService.getRoleFor(UserRole.TESTER)
                status should be set to AccountStatus.INITIATED

        And finally
            Call userService.saveInDatabase to save newly registered tester and return the saved value
*/

        throw new AppException("Method Not Implemented");
    }


}
