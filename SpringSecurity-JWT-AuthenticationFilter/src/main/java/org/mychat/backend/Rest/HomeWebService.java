package org.mychat.backend.Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.mychat.backend.service.HomeService;
import org.mychat.backend.users.User;

@RestController
@RequestMapping("home")
public class HomeWebService {
    @Autowired
    private HomeService homeService;
    @RequestMapping("getPersons")
    public List<User> getPersons(@RequestBody int skip){
        return homeService.getPersons(skip);
    }
}
