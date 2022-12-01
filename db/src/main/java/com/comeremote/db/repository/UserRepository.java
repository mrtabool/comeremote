package com.comeremote.db.repository;

import java.util.Optional;
import java.util.UUID;
import com.comeremote.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndActive(String username, boolean active);

    Optional<User> findByEmailAndActive(String email, boolean active);

    void deleteById(String id);

    Optional<User> findById(Long id);

    Optional<User> findByActivationCode(UUID code);
}
