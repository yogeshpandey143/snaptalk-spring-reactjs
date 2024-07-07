package com.example.snaptalk.SnapTalk.service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.example.snaptalk.SnapTalk.config.JwtProvider;
import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.User;
import com.example.snaptalk.SnapTalk.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImplementation implements UserService {


	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;


	public UserServiceImplementation(
			UserRepository userRepository,
			JwtProvider jwtProvider,
			PasswordEncoder passwordEncoder) {
		
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
		this.passwordEncoder = passwordEncoder;


		
	}
	
	@Override
	public User registerUser(User user) throws UserException {
		
		System.out.println("registered user ------ ");
		
		Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
		
		if (isEmailExist.isPresent()) {
			throw new UserException("Email Already Exist");
		}
		
//		Optional<User> isUsernameTaken=userRepository.findByUsername(user.getUsername());
		
//		if(isUsernameTaken.isPresent()) {
//			throw new UserException("Username Already Taken");
//		}
		
//		if(user.getEmail()== null || 
//				user.getPassword()== null || user.getUsername()==null || user.getName()==null) {
//			throw new UserException("email,password and username are required");
//			
//		}
		
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		
		User newUser=new User();
		
		newUser.setEmail(user.getEmail());
		newUser.setPassword(encodedPassword);
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		
		return userRepository.save(newUser);
		
	}

	
	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> opt = userRepository.findById(userId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new UserException("user not found with userid : "+userId);
	}
	
	


	@Override
	public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
		User followUser=findUserById(followUserId);
		User reqUser=findUserById(reqUserId);
		
//		UserDto follower=new UserDto();
//		follower.setEmail(reqUser.getEmail());
//		follower.setUsername(reqUser.getUsername());
//		follower.setId(reqUser.getId());
//		follower.setName(reqUser.getName());
//		follower.setUserImage(reqUser.getImage());
//		
//	
//		UserDto following=new UserDto();
//		following.setEmail(followUser.getEmail());
//		following.setUsername(followUser.getUsername());
//		following.setId(followUser.getId());
//		following.setName(followUser.getName());
//		following.setUserImage(followUser.getImage());
		
		if(followUser.getFollower().contains(reqUser)) {
			followUser.getFollower().remove(reqUser);
			reqUser.getFollowing().remove(followUser);
		}
		else {
			followUser.getFollower().add(reqUser);
		    reqUser.getFollowing().add(followUser);
		}
		
		userRepository.save(followUser);
		userRepository.save(reqUser);
		
		return "success";
	}


	
	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		System.out.println("email"+email);
		
		Optional<User> user=userRepository.findByEmail(email);
		
		if(user.isEmpty()) {
			throw new UserException("user not exist with email "+email);
		}
		System.out.println("email user "+user.get().getEmail());
		return user.get();
	}
	

	@Override
	public User findUserByEmail(String username) throws UserException {
		
		Optional<User> opt=userRepository.findByEmail(username);
		
		if(opt.isPresent()) {
			User user=opt.get();
			return user;
		}
		
		throw new UserException("user not exist with username "+username);
	}


	@Override
	public List<User> findUsersByUserIds(List<Integer> userIds) {
//		List<User> users= userRepository.findAllUserByUserIds(userIds);
		
		return null;
	}


	@Override
	public Set<User> searchUser(String query) throws UserException {
		Set<User> users=userRepository.findByQuery(query);
		if(users.size()==0) {
			throw new UserException("user not exist");
		}
		return users;
	}


	@Override
	public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
		
		if(updatedUser.getEmail()!= null) {
			existingUser.setEmail(updatedUser.getEmail());	
		}
		if(updatedUser.getBio()!=null) {
			existingUser.setBio(updatedUser.getBio());
		}
		if(updatedUser.getFirstName()!=null) {
			existingUser.setFirstName(updatedUser.getFirstName());
		}
		if(updatedUser.getLastName()!=null) {
			existingUser.setLastName(updatedUser.getLastName());
		}
		if(updatedUser.getMobile()!=null) {
			existingUser.setMobile(updatedUser.getMobile());
		}
		if(updatedUser.getGender()!=null) {
			existingUser.setGender(updatedUser.getGender());
		}
		if(updatedUser.getWebsite()!=null) {
			existingUser.setWebsite(updatedUser.getWebsite());
		}
		if(updatedUser.getImage()!=null) {
			existingUser.setImage(updatedUser.getImage());
		}
		
		
		if(updatedUser.getId()==existingUser.getId()) {
			System.out.println(" u "+updatedUser.getId()+" e "+existingUser.getId());
			throw new UserException("you can't update another user"); 
		}
		
		
		return userRepository.save(existingUser);
		
	}






}
