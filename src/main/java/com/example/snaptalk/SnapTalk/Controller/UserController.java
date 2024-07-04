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


  @PutMapping("/api/user")
  public User updateUser(@RequestHeader("Authentication")String jwt ,@RequestBody User user) throws Exception {

   User reqUser = userService.findUserByJwt(jwt);
    User  updatedUser =userService.updateUser(user,reqUser.getId());
return updatedUser;

  }




  @PutMapping("/api/user/follow/{userId2}")
  public User followUser(@RequestHeader("Authentication")String jwt , @PathVariable Integer userId2)
  {
    User reqUser = userService.findUserByJwt(jwt);

    return userService.followUser(reqUser.getId(),userId2);
  }

  @GetMapping("/api/user/search")
  public List<User> searchUsers(@RequestParam("query") String query)
  {
    return userService.serachUser(query);
  }

  @GetMapping("/api/users/profile")
  public User getUserFromToken(@RequestHeader("Authentication")String jwt)
  {
    if (jwt.startsWith("Bearer ")) {
      jwt = jwt.substring(7);
    }
//    System.out.println("jwt----->"+jwt);

    User user = userService.findUserByJwt(jwt);

    return user;

  }

}