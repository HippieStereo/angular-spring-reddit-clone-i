package com.hippiestereo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hippiestereo.dto.VoteDTO;
import com.hippiestereo.exceptions.PostNotFoundException;
import com.hippiestereo.exceptions.SpringRedditException;
import com.hippiestereo.model.Post;
import com.hippiestereo.model.Vote;
import com.hippiestereo.repository.PostRepository;
import com.hippiestereo.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.hippiestereo.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	
	@Transactional
	public void vote(VoteDTO voteDTO) {
		
		Post post = postRepository.findById(voteDTO.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post not found with ID : " + voteDTO.getPostId()));
		
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		
		// - if already voted
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDTO.getVoteType())) {
			
			throw new SpringRedditException("You already " + voteDTO.getVoteType() + "'d for this post");
			
		}
		
		// - if new vote
		if(UPVOTE.equals(voteDTO.getVoteType())) {
			
			post.setVoteCount(post.getVoteCount() + 1);
			
		} else {
			
			post.setVoteCount(post.getVoteCount() - 1);
			
		}
		
		voteRepository.save(mapToVote(voteDTO, post));
		
		postRepository.save(post);
		
	}

	private Vote mapToVote(VoteDTO voteDTO, Post post) {

		return Vote.builder()
				.voteType(voteDTO.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	
	}
	
}
