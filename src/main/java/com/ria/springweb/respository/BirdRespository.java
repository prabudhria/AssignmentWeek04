package com.ria.springweb.respository;

import com.ria.springweb.entities.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirdRespository extends JpaRepository<Bird, Integer> {

    Bird findByName(String name);
    Bird findByNameAndFamily(String name, String family);
    List<Bird> findAllByVisibleTrue();
}
