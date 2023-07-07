package io.lostemanon.itsworking.service;

import io.lostemanon.itsworking.dto.UserDto;
import java.util.List;

public interface UserService {

  UserDto getById(long id);

  List<UserDto> getAll();

  void save(UserDto userDto);

  void update(long id, UserDto userDto);

  void delete(long id);
}
