package com.example.snaptalk.SnapTalk.utils;

import java.util.List;
import java.util.Set;

import com.example.snaptalk.SnapTalk.model.Post;
import com.example.snaptalk.SnapTalk.model.User;


public class PostUtils {
	
	public static boolean isLikedByReqUser(Post post, User reqUser) {
		
		List<User> like=post.getLiked();
		
		for(User user:like) {
			if(user.getId()==reqUser.getId()) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static boolean isSaved(Post post,User user) {
		
		List<Post> reqUsersPost=user.getSavedPosts();
		
		for(Post item : reqUsersPost) {
			if(item.getId()==post.getId()) {
				return true;
			}
		}
		
		return false;
	}

}
