package com.example.appws.ui.controller;

import com.example.appws.exceptions.UserServiceException;
import com.example.appws.ui.model.request.UpdateUserRequestModel;
import com.example.appws.ui.model.request.UserDetailsRequestModel;
import com.example.appws.ui.model.response.UserRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")    // http://Localhost:8080/users
public class UserController {

    Map<String, UserRest> users = new HashMap<>();
    {
        UserRest userRest = new UserRest();
        userRest.setFirstName("Sergey");
        userRest.setLastName("Ivanov");
        userRest.setEmail("yes@test.com");
        userRest.setUserId("0e66af1a-ca36-4ccd-9fa3-83db646cdc23");
        users.put("0e66af1a-ca36-4ccd-9fa3-83db646cdc23", userRest);
    }

    @GetMapping
    public String getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "limit", defaultValue = "50") int limit,
                           @RequestParam(value = "sort", required = false) String sort) {
        return "get user was called with page = " + page + " and limit = " + limit + " and sort = " + sort;
    }

    /*
    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable String userId) {
        UserRest returnValue = new UserRest();
        returnValue.setFirstName("Sergey");
        returnValue.setLastName("Ivanov");
        returnValue.setEmail("yes@test.com");
        returnValue.setUserId(userId);

        return returnValue;
    }
    */

    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        if (users.containsKey(userId)) {
            return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
        returnValue.setEmail(userDetails.getEmail());

        String userId = UUID.randomUUID().toString();
        returnValue.setUserId(userId);

        users.put(userId, returnValue);

        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequestModel userDetails) {
        UserRest storedUserDetails = users.get(userId);
        storedUserDetails.setFirstName(userDetails.getFirstName());
        storedUserDetails.setLastName(userDetails.getLastName());

        users.put(userId, storedUserDetails);

        return storedUserDetails;
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if (users.remove(userId) == null) {
            throw new UserServiceException("No user with id: " + userId);
        }
        return ResponseEntity.noContent().build();
    }
}
