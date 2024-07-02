package com.example.snaptalk.SnapTalk.Controller;


import com.example.snaptalk.SnapTalk.configsecutiry.JwtProvider;
import com.example.snaptalk.SnapTalk.models.User;
import com.example.snaptalk.SnapTalk.repository.UserRepository;
import com.example.snaptalk.SnapTalk.request.LoginRequest;
import com.example.snaptalk.SnapTalk.response.AuthResponse;
import com.example.snaptalk.SnapTalk.service.CustomerUserDetailsService;
import com.example.snaptalk.SnapTalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

   // /auth/signup
      @PostMapping("/signin")
    public AuthResponse createUser(@RequestBody User user) throws Exception {

        User isExist = userRepository.findByEmail(user.getEmail());
        if(isExist!=null)
        {
            throw new Exception("Already a Account present by this email");
        }

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setGender(user.getGender());
        User  savedUser= userRepository.save(newUser);

        Authentication authentication =new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword() );
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token,"Register Successfully!");
    }



    @PostMapping("/signin")
    public AuthResponse loginUser(@RequestBody LoginRequest loginRequest) throws Exception {

   Authentication authentication = authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token,"Login Successfully!");
      }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails =customerUserDetailsService.loadUserByUsername(email);
        if(userDetails==null) throw new Exception("Bad Credential!");

         if(!passwordEncoder.matches(password,userDetails.getPassword()))
         {
             throw new Exception("Password not matched");
         }

         return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
      }


}
