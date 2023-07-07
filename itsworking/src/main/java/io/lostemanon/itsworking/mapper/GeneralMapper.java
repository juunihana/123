package io.lostemanon.itsworking.mapper;

import io.lostemanon.itsworking.dto.CommentDto;
import io.lostemanon.itsworking.dto.PostDto;
import io.lostemanon.itsworking.dto.UserDto;
import io.lostemanon.itsworking.entity.Comment;
import io.lostemanon.itsworking.entity.Post;
import io.lostemanon.itsworking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface GeneralMapper {

  PostDto postToPostDto(Post post);

  @Mappings({
      @Mapping(source = "postDto.authorId", target = "userId")
  })
  Post postDtoToPost(PostDto postDto);

  UserDto userToUserDto(User user);

  User userDtoToUser(UserDto userDto);

  CommentDto commentToCommentDto(Comment comment);

  @Mappings({
      @Mapping(source = "commentDto.authorId", target = "userId")
  })
  Comment commentDtoToComment(CommentDto commentDto);
}
