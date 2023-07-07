package io.lostemanon.itsworking.service;

import io.lostemanon.itsworking.dto.CommentDto;
import java.util.List;

public interface CommentService {

  CommentDto getById(long id);

  List<CommentDto> getByPostId(long id);

  void save(long id, CommentDto commentDto);

  void delete(long id);
}
