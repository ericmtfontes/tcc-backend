package com.api.tcc.services;

import com.api.tcc.handler.CarAlreadyExistsException;
import com.api.tcc.handler.CarNotFoundException;
import com.api.tcc.handler.NotRentedException;
import com.api.tcc.models.CarModel;
import com.api.tcc.models.EmailModel;
import com.api.tcc.models.RentModel;
import com.api.tcc.models.UserModel;
import com.api.tcc.repositories.CarRepository;
import com.api.tcc.repositories.RentRepository;
import com.api.tcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentRepository rentRepository;

    @Autowired
    EmailService emailService;

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
        carModel.setRented(Boolean.FALSE);
        carModel.setTrash(Boolean.FALSE);
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
        return carRepository.save(carModel);
    }

    public void delete(Long id){
        CarModel carModel = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car id " + id + " not found"));
        carModel.setTrash(Boolean.TRUE);
        carRepository.save(carModel);
    }

    public void createRent(Long idCar, Long idUser, Integer day) {
        CarModel carModel = carRepository.findById(idCar).orElseThrow(() -> new CarNotFoundException("Car id " + idCar + " not found"));
        UserModel userModel = userRepository.findById(idUser).orElseThrow(() -> new CarNotFoundException("User id " + idUser + " not found"));

        if(validateRent(carModel, userModel)){
            userModel.setCar(carModel);
            carModel.setRented(Boolean.TRUE);
            userRepository.save(userModel);
            carRepository.save(carModel);
            double price = carModel.getPricePerDay() * day;
            RentModel rentModel = new RentModel(userModel, carModel, LocalDate.now(), day, price);
            rentRepository.save(rentModel);

            EmailModel emailModel= new EmailModel();

            String bodyMessage = "Obrigado por realizar o aluguel em nossa plataforma. Voce acaba de alugar o veículo " +
                    "de Placa: " + carModel.getPlate() + ", Marca/Modelo: " + carModel.getBrand() + "/" + carModel.getModel() + ", pelo valor de: R$ " + String.format("%.2f", rentModel.getPrice());
            emailModel.setText(bodyMessage);
            emailModel.setEmailTo(userModel.getUsername());
            emailService.sendEmail(emailModel);
        }
    }

    private boolean validateRent(CarModel carModel, UserModel userModel){
        if(carModel.getRented()){
            throw new NotRentedException("Esse veículo já está alugado!");
        }
        if(userModel.getCar() != null){
            throw new NotRentedException("Esse usuario já possui um veículo alugado!");
        }
        return true;
    }
}
