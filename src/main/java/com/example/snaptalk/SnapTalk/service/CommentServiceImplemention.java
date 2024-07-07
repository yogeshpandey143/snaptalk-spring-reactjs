package com.example.snaptalk.SnapTalk.service;

import com.example.snaptalk.SnapTalk.exception.PostException;
import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.Comment;
import com.example.snaptalk.SnapTalk.model.Post;
import com.example.snaptalk.SnapTalk.model.User;
import com.example.snaptalk.SnapTalk.repository.CommentRepository;
import com.example.snaptalk.SnapTalk.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class CommentServiceImplemention implements CommentService{


    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
  CommentRepository commentRepository;


    @Autowired
    private PostRepository postRepository;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {

       User user = userService.findUserById(userId);

       Post post =postService.findePostById(postId);

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);

post.getComments().add(savedComment);

postRepository.save(post);
        return savedComment;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {

   Comment comment = findCommentById(commentId);
   User user =userService.findUserById(userId);
    if(!comment.getLiked().contains(user)){
        comment.getLiked().add(user);
    }
    else{
        comment.getLiked().remove(user);
    }

    return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> opt = commentRepository.findById(commentId);
        if(opt.isEmpty())
        {
            throw new Exception("comment not exist!");
        }

        return opt.get();
    }
}
