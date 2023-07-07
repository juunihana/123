package io.lostemanon.itsworking.repository;

import io.lostemanon.itsworking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
