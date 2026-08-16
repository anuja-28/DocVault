package com.acube.docvault.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acube.docvault.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    java.util.Optional<User> findByEmail(String email);
}