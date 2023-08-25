package com.ria.springweb.respository;

import com.ria.springweb.entities.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirdRespository extends JpaRepository<Bird, Integer> {

    Bird findByName(String name);
}
