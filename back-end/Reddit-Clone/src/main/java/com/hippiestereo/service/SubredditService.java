package com.hippiestereo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippiestereo.dto.SubredditDto;
import com.hippiestereo.model.Subreddit;
import com.hippiestereo.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		
		Subreddit save = subredditRepository.save(mapSubredditDto(subredditDto));
		
		subredditDto.setId(save.getId());
		
		return subredditDto;
		
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {

		return subredditRepository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
		
	}

	private SubredditDto mapToDto(Subreddit subreddit) {
		
		return Subreddit.builder().name(subreddit.getName())
				.id(subreddit.getId())
				.numberOfPosts(subreddit.getPosts().size())
				.build();
		
	}
	
	private Subreddit mapSubredditDto(SubredditDto subredditDto) {

		return Subreddit.builder()
			.name(subredditDto.getName())
			.description(subredditDto.getDescription())
			.build();
		
	}

}