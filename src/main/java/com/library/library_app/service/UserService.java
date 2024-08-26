package com.library.library_app.service;

import com.library.library_app.dto.ServiceResponse;
import com.library.library_app.dto.ReqBodies.UserControllerReqBodies.*;
import com.library.library_app.model.User;


public interface UserService {

    ServiceResponse getAllUsers();

    ServiceResponse getUserById(UserIdDTO userId);

    ServiceResponse createUser(User user);

    ServiceResponse updateUserById(User user);

    ServiceResponse deleteUserById(UserIdDTO userId);

    ServiceResponse rentBookById(UserIdAndBookIdDTO userIdAndBookIdDTO);

    ServiceResponse returnBookById(UserIdAndBookIdDTO userIdAndBookId);
}
