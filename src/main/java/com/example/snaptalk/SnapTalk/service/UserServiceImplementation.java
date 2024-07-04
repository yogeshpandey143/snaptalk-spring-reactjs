package com.example.snaptalk.SnapTalk.service;

import com.example.snaptalk.SnapTalk.configsecutiry.JwtProvider;
import com.example.snaptalk.SnapTalk.models.User;
import com.example.snaptalk.SnapTalk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImplementation  implements UserService{

    @Autowired
    UserRepository userRepository;


    @Override
    public User regidterUser(User user) {
     return null;
    }

    @Override
    public User findUserById(Integer userId) {

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) return user.get();
           return null;
    }

    @Override
    public User findUserByEmail(String email) {
   User user = userRepository.findByEmail(email);
   return user;
    }


    @Override
    public User updateUser(User user ,Integer userId) throws Exception {
        Optional<User> prevUser = userRepository.findById(userId);


        if(!prevUser.isPresent())
        {
            throw new Exception("user not exist with user id "+user.getId());
        }

        User oldUser = prevUser.get();
        if(user.getFirstName()!=null)
        {
            oldUser.setFirstName(user.getFirstName());
        }

        if(user.getLastName()!=null)
        {
            oldUser.setLastName(user.getLastName());
        }
        if(user.getPassword()!=null)
        {
            oldUser.setPassword(user.getPassword());
        }
        if(user.getEmail()!=null)
        {
            oldUser.setEmail(user.getEmail());
        }

        User upadatedUser=userRepository.save(oldUser);
        return upadatedUser;
    }


    public String deleteUser(Integer userId){
        userRepository.deleteById(userId);

        return "user deleted succesfully";
    }



    @Override              //logedin user        other user
    public User followUser(Integer reqUserId, Integer userId2) {
       User  reqUser = findUserById(reqUserId);
       User user2  = findUserById(userId2);
      user2.getFollowers().add(reqUser.getId());
      reqUser.getFollowing().add(user2.getId());

      userRepository.save(reqUser);
        userRepository.save(user2);
     return reqUser;
    }


    @Override
    public List<User> serachUser(String query) {
         return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt){
      String email = JwtProvider.getEmailFromJwtToken(jwt);
     User user =userRepository.findByEmail(email);

     return user;
    }
}
