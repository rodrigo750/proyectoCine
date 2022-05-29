package com.electiva.cine.repository;

import com.electiva.cine.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RoleEntity, Long> {
}
