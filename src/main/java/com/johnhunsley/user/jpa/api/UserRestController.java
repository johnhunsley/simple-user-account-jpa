package com.johnhunsley.user.jpa.api;

import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/12/2016
 *         Time : 14:01
 */
@RestController
@RequestMapping(value = "/user")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity saveUser(@RequestBody UserJpaImpl user) {
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getUserByUsername(@PathVariable(name = "username") final String username) {
        return new ResponseEntity<>(userRepository.findByUsername(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") final Long id) {
        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/page/{pageSize}/{pageNumber}", method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<Page> pageMembers(@PathVariable(name = "pageSize") final int pageSize,
                                            @PathVariable(name = "pageNumber") final int pageNumber) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
