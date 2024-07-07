package com.example.snaptalk.SnapTalk.repository;

import java.util.List;

import com.example.snaptalk.SnapTalk.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Query("select p from Post p where p.user.id=?1")
	public List<Post> findByUserId (Integer userId);

    @Query("SELECT p FROM Post p WHERE p.user.id IN :users ORDER BY p.createdAt DESC")
    public List<Post> findAllPostByUserIdsSortedByDateDesc(@Param("users") List<Integer> userIds);
    
    List<Post> findAllByOrderByCreatedAtDesc();

}
