package io.lostemanon.itsworking.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private long id;
  private String name;
  private String password;
  private String passwordRepeat;
  private String info;
  private LocalDateTime joinTime;
}
