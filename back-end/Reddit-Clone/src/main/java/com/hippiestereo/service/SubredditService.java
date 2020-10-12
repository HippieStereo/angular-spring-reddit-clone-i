package com.hippiestereo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippiestereo.dto.SubredditDTO;
import com.hippiestereo.exceptions.SpringRedditException;
import com.hippiestereo.mapper.SubredditMapper;
import com.hippiestereo.model.Subreddit;
import com.hippiestereo.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	
	@Transactional
	public SubredditDTO save(SubredditDTO subredditDto) {
		
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		
		subredditDto.setId(save.getId());
		
		return subredditDto;
		
	}

	@Transactional(readOnly = true)
	public List<SubredditDTO> getAll() {

		return subredditRepository.findAll()
				.stream()
				.map(subredditMapper::mapSubredditToDto)
				.collect(Collectors.toList());
		
	}

    public SubredditDTO getSubreddit(Long id) {
    	
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        
        return subredditMapper.mapSubredditToDto(subreddit);
        
    }
    
}
