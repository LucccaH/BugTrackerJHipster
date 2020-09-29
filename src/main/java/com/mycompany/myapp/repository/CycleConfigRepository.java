package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CycleConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CycleConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CycleConfigRepository extends JpaRepository<CycleConfig, Long> {
}
