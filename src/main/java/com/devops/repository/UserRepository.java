package com.devops.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.devops.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
