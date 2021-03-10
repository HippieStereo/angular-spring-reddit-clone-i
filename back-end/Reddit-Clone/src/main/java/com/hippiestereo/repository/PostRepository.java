package com.hippiestereo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hippiestereo.model.Post;
import com.hippiestereo.model.Subreddit;
import com.hippiestereo.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);
	
}
