package com.example.snaptalk.SnapTalk.Controller;


import com.example.snaptalk.SnapTalk.models.Post;
import com.example.snaptalk.SnapTalk.models.User;
import com.example.snaptalk.SnapTalk.response.ApiResponse;
import com.example.snaptalk.SnapTalk.service.PostService;
import com.example.snaptalk.SnapTalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;


    @Autowired
    UserService userService;


    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authentication")String jwt,@RequestBody Post post) throws Exception {
       User reqUser= userService.findUserByJwt(jwt);
        Post createdPost = postService.createPost(post, reqUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId,@RequestHeader("Authentication")String jwt) throws Exception {
        User reqUser= userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId,reqUser.getId());
        ApiResponse res = new ApiResponse(message,true);
return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws Exception {
    Post post = postService.findPostById(postId);

    return new ResponseEntity<Post>(post,HttpStatus.FOUND);

    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Post>>  findUsersPost(@PathVariable Integer userId )
    {
    List<Post> posts = postService.findPostByUserId(userId);
    return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public  ResponseEntity<List<Post>> findAllPost()
    {
    List<Post> posts = postService.findAllPost();

    return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }




    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<Post> savedPost(@PathVariable Integer postId , @RequestHeader("Authentication")String jwt) throws Exception {

        User reqUser= userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId,reqUser.getId());

        return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);

    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likedPost(@PathVariable Integer postId ,@RequestHeader("Authentication")String jwt) throws Exception {

        User reqUser= userService.findUserByJwt(jwt);
        Post post = postService.likedPost(postId,reqUser.getId());

        return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);

    }

}
