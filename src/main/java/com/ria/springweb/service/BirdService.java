package com.ria.springweb.service;

import com.ria.springweb.entities.Bird;
import com.ria.springweb.respository.BirdRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BirdService {

    @Autowired
    BirdRespository birdRespository;
    private int id;

    public List<Bird> GetBirds() {
        return (List<Bird>) birdRespository.findAll();
    }

    public Bird GetBird(String name) {
        Bird bird = birdRespository.findByName(name);
        if (bird == null) throw new NoSuchElementException("Can't find Bird of given name");
        else return bird;
    }

    public Bird GetBird(int id) {
        Optional<Bird> bird = birdRespository.findById(id);
        if (bird.isPresent()) {
            return bird.get();
        } else throw new NoSuchElementException("Can't find Bird of given ID");
    }

    public Bird AddBird(Bird bird) {

        return birdRespository.save(bird);
    }

    public Bird UpdateBird(Bird bird) {
        return birdRespository.save(bird);
    }

    public void DeleteBird(int id) {
        if (birdRespository.findById(id).isPresent()) birdRespository.deleteById(id);
        else throw new NoSuchElementException();

    }
}
