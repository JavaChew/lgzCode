package com.archives.service;

import com.archives.model.User;

public interface IUserService {
    User selectUser(long userId);
}
