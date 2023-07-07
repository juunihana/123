package io.lostemanon.itsworking.service.impl;

import io.lostemanon.itsworking.dto.PostDto;
import io.lostemanon.itsworking.entity.Post;
import io.lostemanon.itsworking.mapper.GeneralMapper;
import io.lostemanon.itsworking.repository.PostRepository;
import io.lostemanon.itsworking.service.CommentService;
import io.lostemanon.itsworking.service.PostService;
import io.lostemanon.itsworking.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final UserService userService;
  private final CommentService commentService;
  private final GeneralMapper generalMapper = Mappers.getMapper(GeneralMapper.class);

  @Autowired
  public PostServiceImpl(PostRepository postRepository,
      UserService userService,
      CommentService commentService) {
    this.postRepository = postRepository;
    this.userService = userService;
    this.commentService = commentService;

    postRepository.save(Post.builder()
        .id(1)
        .title("First post")
        .createTime(LocalDateTime.now())
        .editTime(LocalDateTime.now())
        .userId(1)
        .content("Welcome to the main page\n"
            + "There is no information here but in the future there will be even less\n"
            + "What else did you expect?")
        .build());
  }

  @Override
  public PostDto getById(long id) {
    Post post = postRepository.findById(id).orElse(null);
    if (post != null) {
      return PostDto.builder()
          .id(post.getId())
          .authorId(post.getUserId())
          .author(userService.getById(post.getUserId()).getName())
          .createTime(post.getCreateTime())
          .editTime(post.getEditTime())
          .title(post.getTitle())
          .content(post.getContent())
          .comments(commentService.getByPostId(id))
          .build();
    }

    return PostDto.builder()
        .id(0)
        .authorId(1)
        .author("anon")
        .createTime(LocalDateTime.now())
        .editTime(LocalDateTime.now())
        .title("not found")
        .build();
  }

  @Override
  public List<PostDto> getAll() {
    return postRepository.findAll().stream()
        .map(post ->
            PostDto.builder()
                .id(post.getId())
                .authorId(post.getUserId())
                .author(userService.getById(post.getUserId()).getName())
                .createTime(post.getCreateTime())
                .editTime(post.getEditTime())
                .title(post.getTitle())
                .content(post.getContent())
                .build())
        .collect(Collectors.toList());
  }

  @Override
  public List<PostDto> getAllByUserId(long id) {
    return postRepository.findAllByUserId(id).stream()
        .map(post ->
            PostDto.builder()
                .id(post.getId())
                .authorId(post.getUserId())
                .author(userService.getById(post.getUserId()).getName())
                .createTime(post.getCreateTime())
                .editTime(post.getEditTime())
                .title(post.getTitle())
                .content(post.getContent())
                .build())
        .collect(Collectors.toList());
  }

  @Override
  public void save(PostDto postDto) {
    postDto.setCreateTime(LocalDateTime.now());
    postDto.setEditTime(LocalDateTime.now());
    postDto.setAuthorId(1);
    postRepository.save(generalMapper.postDtoToPost(postDto));
  }

  @Override
  public void update(long id, PostDto postDto) {
    if (postRepository.findById(id).orElse(null) != null) {
      postDto.setCreateTime(LocalDateTime.now());
      postDto.setEditTime(LocalDateTime.now());
      postRepository.save(generalMapper.postDtoToPost(postDto));
    }
  }

  @Override
  public void delete(long id) {
    if (postRepository.findById(id).orElse(null) != null) {
      postRepository.deleteById(id);
    }
  }
}
