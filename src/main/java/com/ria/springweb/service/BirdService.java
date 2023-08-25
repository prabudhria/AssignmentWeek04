package com.ria.springweb.service;

import com.ria.springweb.entities.Bird;
import com.ria.springweb.respository.BirdRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNullFields;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BirdService {

    @Autowired
    BirdRespository birdRespository;

    public List<Bird> getBirds() {
        return (List<Bird>) birdRespository.findAll();
    }

    public Bird getBird(String name){
        Bird bird = birdRespository.findByName(name);
        if(bird==null) throw new NoSuchElementException("Can't find Bird of given name");
        else return bird;
    }
    public Bird getBird(int id){
         Optional<Bird> bird = birdRespository.findById(id);
        if(bird.isPresent()){
            return bird.get();
        }
        else throw new NoSuchElementException("Can't find Bird of given ID");
    }

    public Bird addBird(Bird bird) {
        return birdRespository.save(bird);
    }

    public Bird updateBird(Bird bird){
        return birdRespository.save(bird);
    }

    public void deleteBird(int id){
        birdRespository.deleteById(id);
    }
}
