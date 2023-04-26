package com.api.tcc.repositories;

import com.api.tcc.enums.RoleEnum;
import com.api.tcc.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    RoleModel findByRole(RoleEnum role);
}
