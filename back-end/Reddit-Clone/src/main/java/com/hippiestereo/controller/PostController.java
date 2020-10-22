package com.hippiestereo.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hippiestereo.dto.PostRequestDTO;
import com.hippiestereo.dto.PostResponseDTO;
import com.hippiestereo.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequestDTO postRequest) {
		
		postService.save(postRequest);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}

	@GetMapping
	public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
		
		return status(HttpStatus.OK).body(postService.getAllPosts());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long id) {
		
		return status(HttpStatus.OK).body(postService.getPost(id));
		
	}
	
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponseDTO>> getPostsBySubreddit(@PathVariable Long id) {
		
		return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
		
	}	
	
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponseDTO>> getPostsByUserName(@PathVariable String username) {
		
		return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
		
	}
	
}
