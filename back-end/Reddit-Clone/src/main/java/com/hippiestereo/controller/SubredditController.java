package com.hippiestereo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hippiestereo.dto.SubredditDTO;
import com.hippiestereo.service.SubredditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

	private final SubredditService subredditService;
	
	@PostMapping
	public ResponseEntity<SubredditDTO> createSubreddit(@RequestBody SubredditDTO subredditDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
		.body(subredditService.save(subredditDto));
		
	}
	
	@GetMapping
	public ResponseEntity<List<SubredditDTO>> getAllSubreddits() {
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(subredditService.getAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDTO> getSubreddit(@PathVariable Long id){
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(subredditService.getSubreddit(id));
		
	}
	
}
