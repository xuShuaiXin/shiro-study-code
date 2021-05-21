package com.xsx.boot.service;

import com.xsx.boot.model.User;

public interface UserService {
    Boolean addUser(String username, String password);

    Boolean loginUser(String username, String password);

    Boolean logout();

    User selectByUserName(String primaryPrincipal);
}
