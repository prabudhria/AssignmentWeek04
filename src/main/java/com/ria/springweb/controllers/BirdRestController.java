package com.ria.springweb.controllers;

import com.ria.springweb.entities.Bird;
import com.ria.springweb.service.BirdService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class BirdRestController {
    @Autowired
    BirdService birdService;

    @GetMapping(value = "/birds")
    public ResponseEntity<List<Bird>> getBirds(){
        List<Bird> birds = birdService.getBirds();
        return (birds.isEmpty()) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                new ResponseEntity<>(birds, HttpStatus.OK);
    }

    @GetMapping(value = "/birds/{id}")
    public ResponseEntity<Bird> getBirdById(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(birdService.getBird(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.info("Can't find Bird with id \"{}\" ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping(value = "/birds/name")
    public ResponseEntity<Bird> getBirdByName(@RequestParam String name){
        try{
            return new ResponseEntity<>(birdService.getBird(name), HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.info("Can't find Bird of name \"{}\"", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @GetMapping(value = "/birds/nameandfamily")
    public ResponseEntity<Bird> getBirdByNameAndFamily(@RequestParam String name, @RequestParam String family){
        try{
            return new ResponseEntity<>(birdService.getBird(name, family), HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.info("Can't find Bird of name \"{}\"", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping(value = "/birds")
    public ResponseEntity<Bird> addBird(@RequestBody @Valid Bird bird){
        try{
            Bird responseBird = birdService.addBird(bird);
            log.info("Created Bird with id \"{}\" and name \"{}\" ", responseBird.getId(), responseBird.getName());
            return new ResponseEntity<>(responseBird, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            log.info("Bird \"{}\" already exists", bird.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @PutMapping(value = "/birds")
    public ResponseEntity<Bird> updateBird(@RequestBody @Valid Bird bird){
        try{
            Bird responseBird = birdService.updateBird(bird);
            log.info("Updated Bird with id \"{}\" and name \"{}\" ", responseBird.getId(), responseBird.getName());
            return new ResponseEntity<>(responseBird, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            log.info("Bird \"{}\" already exists", bird.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping (value = "/birds/{id}")
    public ResponseEntity<Bird> deleteBird(@PathVariable("id") int id){
        try{
            birdService.deleteBird(id);
            log.info("Deleted Bird with id \"{}\"", id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e){
            log.info("Can't delete Bird with id \"{}\" ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
