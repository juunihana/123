package io.projects.social.repository;

import io.projects.social.entity.PostEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

  Optional<PostEntity> getById(long id);

  List<PostEntity> getAll();
}
