package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CycleConfigService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CycleConfigDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CycleConfig}.
 */
@RestController
@RequestMapping("/api")
public class CycleConfigResource {

    private final Logger log = LoggerFactory.getLogger(CycleConfigResource.class);

    private static final String ENTITY_NAME = "cycleConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CycleConfigService cycleConfigService;

    public CycleConfigResource(CycleConfigService cycleConfigService) {
        this.cycleConfigService = cycleConfigService;
    }

    /**
     * {@code POST  /cycle-configs} : Create a new cycleConfig.
     *
     * @param cycleConfigDTO the cycleConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cycleConfigDTO, or with status {@code 400 (Bad Request)} if the cycleConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cycle-configs")
    public ResponseEntity<CycleConfigDTO> createCycleConfig(@Valid @RequestBody CycleConfigDTO cycleConfigDTO) throws URISyntaxException {
        log.debug("REST request to save CycleConfig : {}", cycleConfigDTO);
        if (cycleConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new cycleConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CycleConfigDTO result = cycleConfigService.save(cycleConfigDTO);
        return ResponseEntity.created(new URI("/api/cycle-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cycle-configs} : Updates an existing cycleConfig.
     *
     * @param cycleConfigDTO the cycleConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cycleConfigDTO,
     * or with status {@code 400 (Bad Request)} if the cycleConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cycleConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cycle-configs")
    public ResponseEntity<CycleConfigDTO> updateCycleConfig(@Valid @RequestBody CycleConfigDTO cycleConfigDTO) throws URISyntaxException {
        log.debug("REST request to update CycleConfig : {}", cycleConfigDTO);
        if (cycleConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CycleConfigDTO result = cycleConfigService.save(cycleConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cycleConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cycle-configs} : get all the cycleConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cycleConfigs in body.
     */
    @GetMapping("/cycle-configs")
    public List<CycleConfigDTO> getAllCycleConfigs() {
        log.debug("REST request to get all CycleConfigs");
        return cycleConfigService.findAll();
    }

    /**
     * {@code GET  /cycle-configs/:id} : get the "id" cycleConfig.
     *
     * @param id the id of the cycleConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cycleConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cycle-configs/{id}")
    public ResponseEntity<CycleConfigDTO> getCycleConfig(@PathVariable Long id) {
        log.debug("REST request to get CycleConfig : {}", id);
        Optional<CycleConfigDTO> cycleConfigDTO = cycleConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cycleConfigDTO);
    }

    /**
     * {@code DELETE  /cycle-configs/:id} : delete the "id" cycleConfig.
     *
     * @param id the id of the cycleConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cycle-configs/{id}")
    public ResponseEntity<Void> deleteCycleConfig(@PathVariable Long id) {
        log.debug("REST request to delete CycleConfig : {}", id);
        cycleConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
