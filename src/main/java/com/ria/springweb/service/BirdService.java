package com.ria.springweb.service;

import com.ria.springweb.entities.Bird;
import com.ria.springweb.respository.BirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BirdService {

    @Autowired
    BirdRepository birdRepository;
    private int id;

    public List<Bird> getBirds() {
        return (List<Bird>) birdRepository.findAllByVisibleTrue();
    }

    public Bird getBird(String name) {
        Bird bird = birdRepository.findByName(name);
        if (bird == null) throw new NoSuchElementException();
        else return bird;
    }
    public Bird getBird(String name, String family) {
        Bird bird = birdRepository.findByNameAndFamily(name, family);
        if (bird == null) throw new NoSuchElementException();
        else return bird;
    }

    public Bird getBird(int id) {
        Optional<Bird> bird = birdRepository.findById(id);
        if (bird.isPresent()) {
            return bird.get();
        } else throw new NoSuchElementException();
    }

    public Bird addBird(Bird bird) {
        if(bird.getAdded()==null) addDate(bird);
        return birdRepository.save(bird);
    }

    public Bird updateBird(Bird bird) {
        if(bird.getAdded()==null) addDate(bird);
        return birdRepository.save(bird);
    }

    public void deleteBird(int id) {
        if (birdRepository.findById(id).isPresent()) birdRepository.deleteById(id);
        else throw new NoSuchElementException();

    }

    public void addDate(Bird bird){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            bird.setAdded(dateFormat.format(date));
    }
}
