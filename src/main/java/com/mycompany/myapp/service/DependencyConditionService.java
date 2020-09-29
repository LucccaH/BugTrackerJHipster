package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DependencyConditionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DependencyCondition}.
 */
public interface DependencyConditionService {

    /**
     * Save a dependencyCondition.
     *
     * @param dependencyConditionDTO the entity to save.
     * @return the persisted entity.
     */
    DependencyConditionDTO save(DependencyConditionDTO dependencyConditionDTO);

    /**
     * Get all the dependencyConditions.
     *
     * @return the list of entities.
     */
    List<DependencyConditionDTO> findAll();


    /**
     * Get the "id" dependencyCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DependencyConditionDTO> findOne(Long id);

    /**
     * Delete the "id" dependencyCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
