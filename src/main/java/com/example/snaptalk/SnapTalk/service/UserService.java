package com.example.snaptalk.SnapTalk.service;

import com.example.snaptalk.SnapTalk.models.User;

import java.util.List;

public interface UserService {

    public User regidterUser(User user);

    public User findUserById(Integer userId);

    public User findUserByEmail(String email);

    public String deleteUser(Integer userId);

    public User updateUser(User user) throws Exception;
    public User  followUser(Integer userId1,Integer userId2);

    public List<User> serachUser(String query);
}
