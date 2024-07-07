package com.example.snaptalk.SnapTalk.controller;

import com.example.snaptalk.SnapTalk.exception.CommentException;
import com.example.snaptalk.SnapTalk.exception.PostException;
import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.Comment;
import com.example.snaptalk.SnapTalk.model.User;
import com.example.snaptalk.SnapTalk.service.CommentService;
import com.example.snaptalk.SnapTalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/{postId}")
	public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment, @PathVariable("postId") Integer postId, @RequestHeader("Authorization")String token) throws PostException, UserException{
		User user = userService.findUserProfileByJwt(token);
		
		Comment createdComment = commentService.createComment(comment, postId, user.getId());
		return new ResponseEntity<Comment>(createdComment,HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/like/{commentId}")
	public ResponseEntity<Comment> likeCommentHandler(@PathVariable Integer commentId, @RequestHeader("Authorization")String token) throws Exception, CommentException {
		System.out.println("----------- like comment id ---------- ");
		User user = userService.findUserProfileByJwt(token);
		Comment likedComment=commentService.likeComment(commentId, user.getId());
		System.out.println("liked comment - : "+likedComment);
		return new ResponseEntity<Comment>(likedComment,HttpStatus.OK);
	}
	
	


}
//