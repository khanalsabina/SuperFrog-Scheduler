package edu.tcu.cs.frog.dao;

import edu.tcu.cs.frog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
