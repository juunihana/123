package io.lostemanon.itsworking.repository;

import io.lostemanon.itsworking.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> getByPostId(long id);
}
