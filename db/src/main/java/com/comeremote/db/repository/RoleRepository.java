package com.comeremote.db.repository;

import java.util.Optional;
import com.comeremote.db.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

        Optional<Role> findByName(String roleName);
}
