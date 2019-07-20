package com.example.appws.userservice;

import com.example.appws.ui.model.request.UpdateUserRequestModel;
import com.example.appws.ui.model.request.UserDetailsRequestModel;
import com.example.appws.ui.model.response.UserRest;

public interface UserService {
    UserRest createUser(UserDetailsRequestModel userDetails);
    UserRest getUser(String userId);
    UserRest updateUser(String userId, UpdateUserRequestModel userDetails);
    UserRest deleteUser(String userId);
}
