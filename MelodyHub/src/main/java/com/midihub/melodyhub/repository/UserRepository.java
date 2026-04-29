package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.appUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<appUser, Long> {}