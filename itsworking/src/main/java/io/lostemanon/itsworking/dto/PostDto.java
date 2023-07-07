package io.lostemanon.itsworking.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

  private long id;
  private long authorId;
  private LocalDateTime createTime;
  private LocalDateTime editTime;
  private String title;
  private String author;
  private String content;
  private List<CommentDto> comments;
}
