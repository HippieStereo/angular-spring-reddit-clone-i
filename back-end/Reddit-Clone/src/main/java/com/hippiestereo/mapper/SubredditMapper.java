package com.hippiestereo.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hippiestereo.dto.SubredditDTO;
import com.hippiestereo.model.Post;
import com.hippiestereo.model.Subreddit;
import com.hippiestereo.model.User;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDTO mapSubredditToDto(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> numberOfPosts) {
		
		return numberOfPosts.size();
		
	}
	
	//@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
	Subreddit mapDtoToSubreddit(SubredditDTO subredditDto, User user);
	
}
