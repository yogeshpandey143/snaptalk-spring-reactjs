package com.example.snaptalk.SnapTalk.service;

import java.util.List;

import com.example.snaptalk.SnapTalk.exception.StoryException;
import com.example.snaptalk.SnapTalk.exception.UserException;
import com.example.snaptalk.SnapTalk.model.Story;

public interface StoryService {

	public Story createStory(Story story, Integer userId) throws UserException;
	
	public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
	
	
}
