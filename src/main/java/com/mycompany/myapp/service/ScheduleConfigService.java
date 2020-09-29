package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ScheduleConfigDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ScheduleConfig}.
 */
public interface ScheduleConfigService {

    /**
     * Save a scheduleConfig.
     *
     * @param scheduleConfigDTO the entity to save.
     * @return the persisted entity.
     */
    ScheduleConfigDTO save(ScheduleConfigDTO scheduleConfigDTO);

    /**
     * Get all the scheduleConfigs.
     *
     * @return the list of entities.
     */
    List<ScheduleConfigDTO> findAll();


    /**
     * Get the "id" scheduleConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScheduleConfigDTO> findOne(Long id);

    /**
     * Delete the "id" scheduleConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
