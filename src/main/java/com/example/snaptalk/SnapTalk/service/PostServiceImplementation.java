package com.example.snaptalk.SnapTalk.service;


import com.example.snaptalk.SnapTalk.models.Post;
import com.example.snaptalk.SnapTalk.models.User;
import com.example.snaptalk.SnapTalk.repository.PostRepository;
import com.example.snaptalk.SnapTalk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    PostRepository postRepository;


    @Autowired
    UserService userService;


    @Autowired
    UserRepository userRepository;

    @Override
    public Post createPost(Post post, Integer userId){

        User user = userService.findUserById(userId);
        Post newPost =new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
//        newPost.setCreatedDate();
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);

         postRepository.save(newPost);

        return newPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {

 Post post =  findPostById(postId);
 User user =userService.findUserById(userId);
 if(post.getUser().getId()!=user.getId())
 {
   throw new Exception("You cant delete another's post");

 }
 else{
     postRepository.deleteById(postId);
 }
        return "post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {

        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty())
        {
          throw new Exception("post not found with id"+postId);
        }
        else return post.get();

    }

    @Override
    public List<Post> findAllPost() {

        return  postRepository.findAll();

    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post =findPostById(postId);
        User user =userService.findUserById(userId);
        if(user.getSavedPost().contains(post))
        {
            user.getSavedPost().remove(post);
        }
        else  user.getSavedPost().add(post);

        userRepository.save(user);

        return post;
    }

    @Override
    public Post likedPost(Integer postId, Integer userId) throws Exception {
       Post post =findPostById(postId);
       User user =userService.findUserById(userId);

       if(post.getLiked().contains(user))
       {
           post.getLiked().remove(user);
       }
       else {
        post.getLiked().add(user);
       }

      postRepository.save(post);
       return post;

    }
}
