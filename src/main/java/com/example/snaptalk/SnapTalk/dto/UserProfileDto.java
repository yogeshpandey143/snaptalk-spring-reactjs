package com.example.snaptalk.SnapTalk.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.example.snaptalk.SnapTalk.model.User;



import lombok.Data;

@Data
public class UserProfileDto {

	private Integer id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String mobile;
	private String website;
	private String bio;
	private String gender;
	private String image;
	
	private List<UserDto> follower = new ArrayList<>();
	
	private List<User> following = new ArrayList<>();


	private List<PostDto> savedPosts = new ArrayList<>();
	

	

}
