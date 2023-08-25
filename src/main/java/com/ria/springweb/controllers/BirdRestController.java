package com.ria.springweb.controllers;

import com.ria.springweb.entities.Bird;
import com.ria.springweb.service.BirdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/birds", method = RequestMethod.GET)
    public ResponseEntity<List<Bird>> getBirds(){
        if(birdService.getBirds().isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(birdService.getBirds().stream().filter(Bird::isVisible).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/birds/{id}", method = RequestMethod.GET)
    public ResponseEntity<Bird> getBirdById(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(birdService.getBird(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @RequestMapping(value = "/birds/name", method = RequestMethod.GET)
    public ResponseEntity<Bird> getBirdByName(@RequestParam String name){
        try{
            return new ResponseEntity<>(birdService.getBird(name), HttpStatus.OK);
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @RequestMapping(value = "/birds", method = RequestMethod.POST)
    public ResponseEntity<Bird> addBird(@RequestBody @Valid Bird bird){
        if(bird.getAdded()==null){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            bird.setAdded(dateFormat.format(date));
        }

        return new ResponseEntity<>(birdService.addBird(bird), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/birds", method = RequestMethod.PUT)

    public ResponseEntity<Bird> updateBird(@RequestBody @Valid Bird bird){
        if(bird.getAdded()==null){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            bird.setAdded(dateFormat.format(date));
        }
        return new ResponseEntity<>(birdService.updateBird(bird), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/birds/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Bird> deleteBird(@PathVariable("id") int id){
        if(birdService.getBird(id)==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else{
            birdService.deleteBird(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

}
