package com.hippiestereo.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hippiestereo.dto.CommentDTO;
import com.hippiestereo.exceptions.PostNotFoundException;
import com.hippiestereo.exceptions.UsernameNotFoundException;
import com.hippiestereo.mapper.CommentMapper;
import com.hippiestereo.model.Comment;
import com.hippiestereo.model.NotificationEmail;
import com.hippiestereo.model.Post;
import com.hippiestereo.model.User;
import com.hippiestereo.repository.CommentRepository;
import com.hippiestereo.repository.PostRepository;
import com.hippiestereo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private static final String POST_URL = "_TEMP_URL_";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailService mailService;
	
	public void save(CommentDTO commentsDTO) {
		
		Post post = postRepository.findById(commentsDTO.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDTO.getPostId().toString()));

		Comment comment = commentMapper.mapDtoToComment(commentsDTO, post, authService.getCurrentUser());
		
		commentRepository.save(comment);
		
		// - Send mail after user comment
		String message = authService.getCurrentUser().getUsername() + " posted a comment on your post." + POST_URL;
		
		sendCommentNotification(message, post.getUser(), authService.getCurrentUser());
		
	}

	private void sendCommentNotification(String message, User userPostOwner, User userCommentOwner) {

		mailService.sendmail(new NotificationEmail("User \"" + userCommentOwner.getUsername() + "\" commented on your post"
				,userPostOwner.getEmail(), message));
		
	}

	public List<CommentDTO> getAllCommentsForPost(Long postId) {
		
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		
		return commentRepository.findByPost(post).stream()
				.map(commentMapper::mapCommentToDto)
				.collect(toList());
		
	}

	public List<CommentDTO> getAllCommentsForUser(String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return commentRepository.findAllByUser(user)
				.stream()
				.map(commentMapper::mapCommentToDto)
				.collect(toList());
		
	}
	
}
