package io.lostemanon.itsworking.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

  private long id;
  private long postId;
  private long authorId;
  private String author;
  private LocalDateTime createTime;
  private String content;
}
