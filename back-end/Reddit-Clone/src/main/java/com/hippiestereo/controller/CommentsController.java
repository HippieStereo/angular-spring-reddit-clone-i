package com.hippiestereo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hippiestereo.dto.CommentDTO;
import com.hippiestereo.model.User;
import com.hippiestereo.service.CommentService;

import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {

	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentDTO commentsDTO) {
		
		commentService.save(commentsDTO);
		
		return new ResponseEntity<>(CREATED);
		
	}
	
	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
		
		return ResponseEntity.status(OK)
				.body(commentService.getAllCommentsForPost(postId));
		
	}
	
	@GetMapping("/by-user/{username}")
	public ResponseEntity<List<CommentDTO>> getAllCommentsForUser(@PathVariable String username) {
		
		return ResponseEntity.status(OK)
				.body(commentService.getAllCommentsForUser(username));
		
	}
	
}
