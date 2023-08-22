package com.api.tcc.controllers;

import com.api.tcc.models.CarModel;
import com.api.tcc.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarModel> findById(@PathVariable Long id){
        return new ResponseEntity<>(carService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CarModel>> findAll(@RequestParam(required = false) String search){
        return new ResponseEntity<>(carService.findAll(search), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody CarModel carModel){
        carService.create(carModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarModel> update(@RequestBody CarModel car, @PathVariable Long id){
        return new ResponseEntity<>(carService.update(car, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
