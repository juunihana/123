package io.projects.social.mapper;

import io.projects.social.dto.PostDto;
import io.projects.social.entity.PostEntity;
import io.projects.social.util.TimeUtils;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface PostMapper {

  @Mapping(source = "dateTime", target = "dateTime", qualifiedByName = "dateTimeMapping")
  PostDto postEntityToDto(PostEntity entity);

  @Named("dateTimeMapping")
  static String dateTimeMapping(LocalDateTime dateTimeEntity) {
    return TimeUtils.dateTimeToString(dateTimeEntity);
  }
}
