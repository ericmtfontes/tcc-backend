package com.api.tcc.services;

import com.api.tcc.handler.CarAlreadyExistsException;
import com.api.tcc.handler.CarNotFoundException;
import com.api.tcc.models.CarModel;
import com.api.tcc.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public CarModel findById(Long id){
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car id " + id + " not found"));
    }

    public List<CarModel> findAll(String search){
        if(search != null){
            if(carRepository.findAllNonRented(search).isEmpty()){
                throw new CarNotFoundException("Cars not found");
            }else{
                return carRepository.findAllNonRented(search);
            }
        }else{
            if(carRepository.findAllNonRented().isEmpty()){
                throw new CarNotFoundException("Cars not found");
            }else{
                return carRepository.findAllNonRented();
            }
        }
    }

    public void create(CarModel carModel){
        if(carRepository.findByPlate(carModel.getPlate()) != null){
            throw new CarAlreadyExistsException("Car already exists");
        }
        carModel.setRented(false);
        carRepository.save(carModel);
    }

    public CarModel update(CarModel car, Long id){
        CarModel carModel = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car id " + id + " not found"));
        carModel.setPlate(car.getPlate());
        carModel.setBrand(car.getBrand());
        carModel.setModel(car.getModel());
        carModel.setCategory(car.getCategory());
        carModel.setImage(car.getImage());
        carModel.setYear(car.getYear());
        carModel.setDescription(car.getDescription());
        carModel.setPricePerDay(car.getPricePerDay());
        carModel.setRented(Boolean.FALSE);
        return carRepository.save(carModel);
    }

    public void delete(Long id){
        CarModel carModel = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car id " + id + " not found"));
        carRepository.delete(carModel);
    }
}
