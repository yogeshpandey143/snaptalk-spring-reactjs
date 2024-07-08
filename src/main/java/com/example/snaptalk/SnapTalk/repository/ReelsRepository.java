package com.example.snaptalk.SnapTalk.repository;

import com.example.snaptalk.SnapTalk.model.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels,Integer> {
public List<Reels> findByUserId(Integer userId);
}
