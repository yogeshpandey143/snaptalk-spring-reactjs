package com.example.snaptalk.SnapTalk.service;

import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.Reels;
import com.example.snaptalk.SnapTalk.model.User;

import java.util.List;

public interface ReelsService {

    public Reels createReel(Reels reel, User user );
    public List<Reels> findAllReels();

    public List<Reels> findUsersReel(Integer userId) throws UserException;
}
