package com.bej.authentication.service;

import com.bej.authentication.domain.User;
import com.bej.authentication.exception.UserAlreadyExistsException;
import com.bej.authentication.exception.InvalidCredentialsException;

public interface IUserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByUserIdAndPassword(String userId, String password) throws InvalidCredentialsException;
}
