package com.archives.dao;

import com.archives.model.User;

public interface IUserDao {
    User selectUser(long id);
}
