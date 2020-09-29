package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.DependencyConditionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.DependencyConditionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DependencyCondition}.
 */
@RestController
@RequestMapping("/api")
public class DependencyConditionResource {

    private final Logger log = LoggerFactory.getLogger(DependencyConditionResource.class);

    private static final String ENTITY_NAME = "dependencyCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DependencyConditionService dependencyConditionService;

    public DependencyConditionResource(DependencyConditionService dependencyConditionService) {
        this.dependencyConditionService = dependencyConditionService;
    }

    /**
     * {@code POST  /dependency-conditions} : Create a new dependencyCondition.
     *
     * @param dependencyConditionDTO the dependencyConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dependencyConditionDTO, or with status {@code 400 (Bad Request)} if the dependencyCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dependency-conditions")
    public ResponseEntity<DependencyConditionDTO> createDependencyCondition(@RequestBody DependencyConditionDTO dependencyConditionDTO) throws URISyntaxException {
        log.debug("REST request to save DependencyCondition : {}", dependencyConditionDTO);
        if (dependencyConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new dependencyCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DependencyConditionDTO result = dependencyConditionService.save(dependencyConditionDTO);
        return ResponseEntity.created(new URI("/api/dependency-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dependency-conditions} : Updates an existing dependencyCondition.
     *
     * @param dependencyConditionDTO the dependencyConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dependencyConditionDTO,
     * or with status {@code 400 (Bad Request)} if the dependencyConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dependencyConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dependency-conditions")
    public ResponseEntity<DependencyConditionDTO> updateDependencyCondition(@RequestBody DependencyConditionDTO dependencyConditionDTO) throws URISyntaxException {
        log.debug("REST request to update DependencyCondition : {}", dependencyConditionDTO);
        if (dependencyConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DependencyConditionDTO result = dependencyConditionService.save(dependencyConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dependencyConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dependency-conditions} : get all the dependencyConditions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dependencyConditions in body.
     */
    @GetMapping("/dependency-conditions")
    public List<DependencyConditionDTO> getAllDependencyConditions() {
        log.debug("REST request to get all DependencyConditions");
        return dependencyConditionService.findAll();
    }

    /**
     * {@code GET  /dependency-conditions/:id} : get the "id" dependencyCondition.
     *
     * @param id the id of the dependencyConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dependencyConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dependency-conditions/{id}")
    public ResponseEntity<DependencyConditionDTO> getDependencyCondition(@PathVariable Long id) {
        log.debug("REST request to get DependencyCondition : {}", id);
        Optional<DependencyConditionDTO> dependencyConditionDTO = dependencyConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dependencyConditionDTO);
    }

    /**
     * {@code DELETE  /dependency-conditions/:id} : delete the "id" dependencyCondition.
     *
     * @param id the id of the dependencyConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dependency-conditions/{id}")
    public ResponseEntity<Void> deleteDependencyCondition(@PathVariable Long id) {
        log.debug("REST request to delete DependencyCondition : {}", id);
        dependencyConditionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
