package com.example.snaptalk.SnapTalk.dto.mapper;

import com.example.snaptalk.SnapTalk.dto.PostDto;
import com.example.snaptalk.SnapTalk.dto.UserDto;
import com.example.snaptalk.SnapTalk.model.Post;
import com.example.snaptalk.SnapTalk.model.User;
import com.example.snaptalk.SnapTalk.utils.PostUtils;

import java.util.ArrayList;
import java.util.List;



public class PostDtoMapper {
	
	public static PostDto toPostDto(Post post, User user) {
		
		UserDto userDto=UserDtoMapper.userDTO(post.getUser());
		List<User> likedUsers=new ArrayList<>(post.getLiked());
		List<UserDto> userDtos=UserDtoMapper.userDTOS(likedUsers);

		
		PostDto postDto=new PostDto();
		postDto.setCaption(post.getCaption());
		postDto.setCreatedAt(post.getCreatedAt());
		postDto.setId(post.getId());
		postDto.setImage(post.getImage());
		postDto.setUser(userDto);
		postDto.setLiked(userDtos);
		postDto.setLikedByRequser(PostUtils.isLikedByReqUser(post, user));
		postDto.setSavedByRequser(PostUtils.isSaved(post, user));
		
		return postDto;
		
	}
	
	public static List<PostDto> toPostDtos(List<Post> posts, User user){
		List<PostDto> postDtos=new ArrayList<>();
		
		for(Post post:posts) {
			PostDto postDto=toPostDto(post,user);
			postDtos.add(postDto);
		}
		return postDtos;
	}

}
