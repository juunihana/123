package io.lostemanon.itsworking.repository;

import io.lostemanon.itsworking.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByUserId(long id);
}
