package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CycleConfigDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CycleConfig}.
 */
public interface CycleConfigService {

    /**
     * Save a cycleConfig.
     *
     * @param cycleConfigDTO the entity to save.
     * @return the persisted entity.
     */
    CycleConfigDTO save(CycleConfigDTO cycleConfigDTO);

    /**
     * Get all the cycleConfigs.
     *
     * @return the list of entities.
     */
    List<CycleConfigDTO> findAll();


    /**
     * Get the "id" cycleConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CycleConfigDTO> findOne(Long id);

    /**
     * Delete the "id" cycleConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
