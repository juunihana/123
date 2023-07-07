package io.lostemanon.itsworking.service.impl;

import io.lostemanon.itsworking.dto.UserDto;
import io.lostemanon.itsworking.entity.User;
import io.lostemanon.itsworking.mapper.GeneralMapper;
import io.lostemanon.itsworking.repository.UserRepository;
import io.lostemanon.itsworking.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final GeneralMapper generalMapper = Mappers.getMapper(GeneralMapper.class);

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;

    userRepository.save(User.builder()
        .id(1)
        .name("anon")
        .password("")
        .info("anonimous user")
        .joinTime(LocalDateTime.now())
        .build());
  }

  @Override
  public UserDto getById(long id) {
    return generalMapper.userToUserDto(userRepository.findById(id).orElse(
        User.builder()
            .name("not found")
            .build()
    ));
  }

  @Override
  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .map(generalMapper::userToUserDto)
        .collect(Collectors.toList());
  }

  @Override
  public void save(UserDto userDto) {
    userDto.setJoinTime(LocalDateTime.now());
    userRepository.save(generalMapper.userDtoToUser(userDto));
  }

  @Override
  public void update(long id, UserDto userDto) {
    if (userRepository.findById(id).orElse(null) != null) {
      userRepository.save(generalMapper.userDtoToUser(userDto));
    }
  }

  @Override
  public void delete(long id) {
    if (userRepository.findById(id).orElse(null) != null) {
      userRepository.deleteById(id);
    }
  }
}
