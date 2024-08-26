package com.library.library_app.repository;


import java.util.List;
import java.util.Optional;

import com.library.library_app.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select new com.library.library_app.model.User(u.id, u.name, u.surname) from User u")
    List<User> getAllUsers();

    @Query(value = "select new com.library.library_app.model.User(u.id, u.name, u.surname) from User u where u.id = :id")
    Optional<User> getUserById(@Param("id") Long id);
}
