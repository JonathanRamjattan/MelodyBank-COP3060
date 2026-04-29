package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {}