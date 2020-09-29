package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ScheduleConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScheduleConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleConfigRepository extends JpaRepository<ScheduleConfig, Long> {
}
