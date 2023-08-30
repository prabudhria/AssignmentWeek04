package com.ria.springweb.controllers;

import com.ria.springweb.entities.Bird;
import com.ria.springweb.service.BirdService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class BirdRestController {
    @Autowired
    BirdService birdService;

    private final Logger log = LoggerFactory.getLogger(BirdRestController.class);

    @GetMapping(value = "/birds")
    public ResponseEntity<List<Bird>> GetBirds(){
        if(birdService.GetBirds().isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(birdService.GetBirds().stream().filter(Bird::isVisible).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/birds/{id}")
    public ResponseEntity<Bird> GetBirdById(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(birdService.GetBird(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.info("Can't find Bird with id \"{}\" ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping(value = "/birds/name")
    public ResponseEntity<Bird> GetBirdByName(@RequestParam String name){
        try{
            return new ResponseEntity<>(birdService.GetBird(name), HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.info("Can't find Bird of name \"{}\"", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping(value = "/birds")
    public ResponseEntity<Bird> AddBird(@RequestBody @Valid Bird bird){
        if(bird.getAdded()==null){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            bird.setAdded(dateFormat.format(date));
        }
        try{
            Bird responseBird = birdService.AddBird(bird);
            log.info("Created Bird with id \"{}\" and name \"{}\" ", responseBird.getId(), responseBird.getName());
            return new ResponseEntity<>(responseBird, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            log.info("Bird \"{}\" already exists", bird.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @PutMapping(value = "/birds")
    public ResponseEntity<Bird> UpdateBird(@RequestBody @Valid Bird bird){
        if(bird.getAdded()==null){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            bird.setAdded(dateFormat.format(date));
        }
        try{
            Bird responseBird = birdService.UpdateBird(bird);
            log.info("Updated Bird with id \"{}\" and name \"{}\" ", responseBird.getId(), responseBird.getName());
            return new ResponseEntity<>(responseBird, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            log.info("Bird \"{}\" already exists", bird.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping (value = "/birds/{id}")
    public ResponseEntity<Bird> DeleteBird(@PathVariable("id") int id){
        try{
            birdService.DeleteBird(id);
            log.info("Deleted Bird with id \"{}\"", id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e){
            log.info("Can't delete Bird with id \"{}\" ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
