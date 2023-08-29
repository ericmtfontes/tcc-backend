package com.api.tcc.repositories;

import com.api.tcc.models.CarModel;
import com.api.tcc.models.RentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<RentModel, Long> {
}
