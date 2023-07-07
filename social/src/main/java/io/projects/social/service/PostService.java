package io.projects.social.service;

import io.projects.social.dto.PostDto;
import java.util.List;

public interface PostService {

  PostDto getById(long id);

  List<PostDto> getAll();

}
