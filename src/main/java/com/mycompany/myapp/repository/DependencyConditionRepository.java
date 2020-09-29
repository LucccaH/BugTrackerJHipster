package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DependencyCondition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DependencyCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependencyConditionRepository extends JpaRepository<DependencyCondition, Long> {
}
