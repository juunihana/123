package io.lostemanon.itsworking.service;

import io.lostemanon.itsworking.dto.PostDto;
import java.util.List;

public interface PostService {

  PostDto getById(long id);

  List<PostDto> getAll();

  List<PostDto> getAllByUserId(long id);

  void save(PostDto postDto);

  void update(long id, PostDto postDto);

  void delete(long id);
}
