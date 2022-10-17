package com.atijerarachel.checklists.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.atijerarachel.checklists.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
  
  //Get total number of users
  @Query(value = "SELECT COUNT(user.id) FROM user", nativeQuery = true)
  int getTotalNumberOfUsers();
}