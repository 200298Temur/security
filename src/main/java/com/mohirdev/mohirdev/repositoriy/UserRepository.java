package com.mohirdev.mohirdev.repositoriy;

import com.mohirdev.mohirdev.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String  username);
}
