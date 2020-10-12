package com.hippiestereo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hippiestereo.dto.CommentsDTO;
import com.hippiestereo.model.Comment;
import com.hippiestereo.model.Post;
import com.hippiestereo.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment mapDtoToComment(CommentsDTO commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDTO mapCommentToDto(Comment comment);
}