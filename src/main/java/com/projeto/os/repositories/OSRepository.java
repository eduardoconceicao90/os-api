package com.projeto.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.os.entities.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Long> {

}
