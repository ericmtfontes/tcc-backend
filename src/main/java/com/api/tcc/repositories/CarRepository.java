package com.api.tcc.repositories;

import com.api.tcc.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {

    @Query("SELECT c FROM CarModel c WHERE c.rented = 0")
    List<CarModel> findAllNonRented();
    @Query("SELECT c FROM CarModel c WHERE CONCAT(c.category, c.pricePerDay, c.year) LIKE %?1%" +
            "AND c.rented = 0")
    List<CarModel> findAllNonRented(String search);
    CarModel findByPlate(String plate);
}
