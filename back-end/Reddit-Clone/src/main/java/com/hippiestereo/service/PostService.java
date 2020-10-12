package com.hippiestereo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippiestereo.dto.PostRequestDTO;
import com.hippiestereo.dto.PostResponseDTO;
import com.hippiestereo.model.Post;
import com.hippiestereo.model.Subreddit;
import com.hippiestereo.model.User;
import com.hippiestereo.repository.PostRepository;
import com.hippiestereo.repository.SubredditRepository;
import com.hippiestereo.repository.UserRepository;
import com.hippiestereo.exceptions.PostNotFoundException;
import com.hippiestereo.exceptions.SubredditNotFoundException;
import com.hippiestereo.mapper.PostMapper;

import static java.util.stream.Collectors.toList;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

	private SubredditRepository subredditRepository;
	private PostRepository postRepository;
	private UserRepository userRepository;
	private final AuthService authService;
	private PostMapper postMapper;
	
	@Transactional
	public void save (PostRequestDTO postRequest){
		
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		
		postRepository.save(postMapper.mapDtoToPost(
				postRequest, subreddit, authService.getCurrentUser()));
		
	}
	
	@Transactional(readOnly = true)
	public PostResponseDTO getPost(Long id) {
		
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id.toString()));
		
		return postMapper.mapPostToDto(post);
		
	}
	
	@Transactional(readOnly = true)
	public List<PostResponseDTO> getAllPosts(){
		
		return postRepository.findAll()
				.stream()
				.map(postMapper::mapPostToDto)
				.collect(toList());
		
	}
	
	@Transactional(readOnly = true)
	public List<PostResponseDTO> getPostsBySubreddit(Long suredditId){
		
		Subreddit subreddit = subredditRepository.findById(suredditId)
				.orElseThrow(() -> new SubredditNotFoundException(suredditId.toString()));
		
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		
		return posts.stream().map(postMapper::mapPostToDto).collect(toList());
		
	}
	
	@Transactional(readOnly = true)
	public List<PostResponseDTO> getPostsByUsername(String username){
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return postRepository.findByUser(user)
				.stream()
				.map(postMapper::mapPostToDto)
				.collect(toList());
		
	}
	
}
