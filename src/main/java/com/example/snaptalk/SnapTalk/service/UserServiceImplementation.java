package com.example.snaptalk.SnapTalk.service;

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
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setGender(user.getGender());
        User  savedUser= userRepository.save(newUser);
        return savedUser;
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
    public User updateUser(User user) throws Exception {
        Optional<User> prevUser = userRepository.findById(user.getId());


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

        User upadatedUser=userRepository.save(oldUser);
        return upadatedUser;
    }


    public String deleteUser(Integer userId){
        userRepository.deleteById(userId);

        return "user deleted succesfully";
    }



    @Override
    public User followUser(Integer userId1, Integer userId2) {
       User user1 = findUserById(userId1);
       User user2  = findUserById(userId2);
      user2.getFollowers().add(user1.getId());
      user1.getFollowing().add(user2.getId());

      userRepository.save(user1);
        userRepository.save(user2);
     return user1;
    }


    @Override
    public List<User> serachUser(String query) {
         return userRepository.searchUser(query);
    }
}
