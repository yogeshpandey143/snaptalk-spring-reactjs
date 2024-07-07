package com.example.snaptalk.SnapTalk.service;

import com.example.snaptalk.SnapTalk.exception.PostException;
import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.Comment;

public interface CommentService {


    public Comment createComment( Comment comment, Integer postId ,Integer userId) throws UserException, PostException;


    public Comment likeComment(Integer commentId ,Integer userId) throws Exception;

    public Comment findCommentById(Integer commentId) throws Exception;

}
