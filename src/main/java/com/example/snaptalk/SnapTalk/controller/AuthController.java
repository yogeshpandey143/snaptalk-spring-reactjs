package com.example.snaptalk.SnapTalk.controller;

import java.util.Optional;

import com.example.snaptalk.SnapTalk.config.JwtProvider;
import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.User;
import com.example.snaptalk.SnapTalk.repository.UserRepository;
import com.example.snaptalk.SnapTalk.request.LoginRequest;
import com.example.snaptalk.SnapTalk.response.AuthResponse;
import com.example.snaptalk.SnapTalk.service.CustomeUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {


	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomeUserServiceImplementation customUserDetails;
	@Autowired

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
			CustomeUserServiceImplementation customUserDetails) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.customUserDetails = customUserDetails;

	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFirstName();
		String lastName=user.getLastName();
		String gender=user.getGender();
		
		System.out.println("email "+email+" - "+fullName);

		Optional<User> isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist.isPresent()) {

			throw new UserException("Email Is Already Used With Another Account");
		}

	
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFirstName(fullName);
		createdUser.setLastName(lastName);
		createdUser.setGender(gender);
		createdUser.setPassword(passwordEncoder.encode(password));
	

		User savedUser = userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Registration Successfull");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) throws UserException {

		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
//		Optional<User> user = userRepository.findByEmail(username);
		User user=new User();
//		if(user.isEmpty()) {
//			throw new UserException("user not found with username  "+ username);
//		}

		System.out.println(username + " ----- " + password);

		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
//
		String token = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();

		authResponse.setMessage("Login Success");
		authResponse.setJwt(token);

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetails.loadUserByUsername(username);

		System.out.println("sign in userDetails - " + userDetails);

		if (userDetails == null) {
			System.out.println("sign in userDetails - null " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("sign in userDetails - password not match " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
