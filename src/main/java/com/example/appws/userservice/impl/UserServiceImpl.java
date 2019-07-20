package com.example.appws.userservice.impl;

import com.example.appws.shared.Utils;
import com.example.appws.ui.model.request.UpdateUserRequestModel;
import com.example.appws.ui.model.request.UserDetailsRequestModel;
import com.example.appws.ui.model.response.UserRest;
import com.example.appws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    Utils utils;

    Map<String, UserRest> users = new HashMap<>();
    {
        UserRest userRest = new UserRest();
        userRest.setFirstName("Sergey");
        userRest.setLastName("Ivanov");
        userRest.setEmail("yes@test.com");
        userRest.setUserId("0e66af1a-ca36-4ccd-9fa3-83db646cdc23");
        users.put("0e66af1a-ca36-4ccd-9fa3-83db646cdc23", userRest);
    }

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(Utils utils) {
        this.utils = utils;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
        returnValue.setEmail(userDetails.getEmail());

        String userId = utils.generateUserId();
        returnValue.setUserId(userId);

        users.put(userId, returnValue);

        return returnValue;
    }

    @Override
    public UserRest getUser(String userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            return null;
        }
    }

    @Override
    public UserRest updateUser(String userId, UpdateUserRequestModel userDetails) {
        UserRest storedUser = users.get(userId);
        storedUser.setFirstName(userDetails.getFirstName());
        storedUser.setLastName(userDetails.getLastName());

        users.put(userId, storedUser);

        return storedUser;
    }

    @Override
    public UserRest deleteUser(String userId) {
        return users.remove(userId);
    }
}
