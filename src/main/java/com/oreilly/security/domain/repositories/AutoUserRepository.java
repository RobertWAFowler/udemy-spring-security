package com.oreilly.security.domain.repositories;

import com.oreilly.security.domain.entities.AutoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoUserRepository extends JpaRepository<AutoUser, Long> {

    public AutoUser findByUsername(String username);
}
