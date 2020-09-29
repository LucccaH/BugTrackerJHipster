package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AlarmConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlarmConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmConfigRepository extends JpaRepository<AlarmConfig, Long> {
}
