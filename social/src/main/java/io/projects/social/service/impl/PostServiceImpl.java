package io.projects.social.service.impl;

import io.projects.social.dto.PostDto;
import io.projects.social.mapper.PostMapper;
import io.projects.social.repository.PostRepository;
import io.projects.social.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper mapper = Mappers.getMapper(PostMapper.class);

  @Autowired
  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public PostDto getById(long id) {
    return mapper.postEntityToDto(postRepository.getById(id).get());
  }

  @Override
  public List<PostDto> getAll() {
    return postRepository.getAll().stream()
        .map(PostMapper::postEntityToDto)
        .collect(Collectors.toList());
  }
}
