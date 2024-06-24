package com.example.snaptalk.SnapTalk.Controller;


import com.example.snaptalk.SnapTalk.models.Post;
import com.example.snaptalk.SnapTalk.response.ApiResponse;
import com.example.snaptalk.SnapTalk.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;


    @PostMapping("/posts/user/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Integer userId) throws Exception {
        Post createdPost = postService.createPost(post, userId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception {
String message = postService.deletePost(postId,userId);
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




    @PutMapping("/posts/save/{postId}/user/{userId}")
    public ResponseEntity<Post> savedPost(@PathVariable Integer postId , @PathVariable Integer userId) throws Exception {
        Post post = postService.savedPost(postId,userId);

        return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);

    }

    @PutMapping("/posts/like/{postId}/user/{userId}")
    public ResponseEntity<Post> likedPost(@PathVariable Integer postId , @PathVariable Integer userId) throws Exception {
        Post post = postService.likedPost(postId,userId);

        return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);

    }

}
