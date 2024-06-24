package com.example.snaptalk.SnapTalk.Controller;


import com.example.snaptalk.SnapTalk.models.User;
import com.example.snaptalk.SnapTalk.repository.UserRepository;
import com.example.snaptalk.SnapTalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;



    @PostMapping("/user")
    public User createUser(@RequestBody User user)
    {
        return userService.regidterUser(user);
    }

    @GetMapping("/api/user/{userId}")
    public User getUserById(@PathVariable("userId") Integer id){

        return userService.findUserById(id);
    }

    @GetMapping("/api/user")
    public List<User> getUsers(){

        List<User> users= userRepository.findAll();
       return users;
    }

    @GetMapping("/api/user/email")
    public User findByEmail(@RequestBody String email)
    {

        User user = userService.findUserByEmail(email);
        return  user;
    }

    @DeleteMapping("/api/user/{userId}")
    public String deleteUser( @PathVariable("userId")Integer id)
    {
        return userService.deleteUser(id);
    }


    @PutMapping("/api/user/{userId}")
    public User updateUser(@RequestBody User user ,@PathVariable Integer userId) throws Exception {
       User  myUser =userService.updateUser(user);
       if(myUser.getId()== userId) return  myUser;
       else return null;
    }




    @PutMapping("/api/user/follow/{userId1}/{userId2}")
    public User followUser(@PathVariable Integer userId1  , @PathVariable Integer userId2)
    {
      return userService.followUser(userId1,userId2);
    }

    @GetMapping("/api/user/search")
    public List<User> searchUsers(@RequestParam("query") String query)
    {
        return userService.serachUser(query);
    }

}
