package com.hippiestereo.repository;

import com.hippiestereo.model.Comment;
import com.hippiestereo.model.Post;
import com.hippiestereo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
