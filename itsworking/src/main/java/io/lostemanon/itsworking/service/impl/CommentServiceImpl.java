package io.lostemanon.itsworking.service.impl;

import io.lostemanon.itsworking.dto.CommentDto;
import io.lostemanon.itsworking.entity.Comment;
import io.lostemanon.itsworking.mapper.GeneralMapper;
import io.lostemanon.itsworking.repository.CommentRepository;
import io.lostemanon.itsworking.service.CommentService;
import io.lostemanon.itsworking.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final UserService userService;
  private final GeneralMapper generalMapper = Mappers.getMapper(GeneralMapper.class);

  @Autowired
  public CommentServiceImpl(CommentRepository commentRepository,
      UserService userService) {
    this.commentRepository = commentRepository;
    this.userService = userService;
  }

  @Override
  public CommentDto getById(long id) {
    return generalMapper.commentToCommentDto(commentRepository.findById(id).orElse(
        Comment.builder()
            .content("not found")
            .build()
    ));
  }

  @Override
  public List<CommentDto> getByPostId(long id) {
    return commentRepository.getByPostId(id).stream()
        .map(comment -> CommentDto.builder()
            .id(comment.getId())
            .author(userService.getById(comment.getUserId()).getName())
            .createTime(comment.getCreateTime())
            .authorId(userService.getById(comment.getUserId()).getId())
            .content(comment.getContent())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public void save(long id, CommentDto commentDto) {
    commentDto.setPostId(id);
    commentDto.setAuthorId(1);
    commentDto.setCreateTime(LocalDateTime.now());
    commentRepository.save(generalMapper.commentDtoToComment(commentDto));
  }

  @Override
  public void delete(long id) {
    if (commentRepository.findById(id).orElse(null) != null) {
      commentRepository.deleteById(id);
    }
  }
}
