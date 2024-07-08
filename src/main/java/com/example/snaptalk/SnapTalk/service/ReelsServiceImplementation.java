package com.example.snaptalk.SnapTalk.service;

import java.util.List;

import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.Reels;
import com.example.snaptalk.SnapTalk.model.User;
import com.example.snaptalk.SnapTalk.repository.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReelsServiceImplementation implements ReelsService {
	
	@Autowired
	private ReelsRepository reelsRepositoy;
	
	@Autowired
	private UserService userSerive;

	@Override
	public Reels createReel(Reels reel, User user) {
		Reels createdReel = new Reels();
		
		createdReel.setTitle(reel.getTitle());
		createdReel.setUser(user);
		createdReel.setVideo(reel.getVideo());
		
		return reelsRepositoy.save(createdReel);
	}

	@Override
	public List<Reels> findAllReels() {
		
		return reelsRepositoy.findAll();
	}

	@Override
	public List<Reels> findUsersReel(Integer userId) throws UserException {
		userSerive.findUserById(userId);
		return reelsRepositoy.findByUserId(userId);
	}

}
