package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ScheduleConfigService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ScheduleConfigDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.ScheduleConfig}.
 */
@RestController
@RequestMapping("/api")
public class ScheduleConfigResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleConfigResource.class);

    private static final String ENTITY_NAME = "scheduleConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleConfigService scheduleConfigService;

    public ScheduleConfigResource(ScheduleConfigService scheduleConfigService) {
        this.scheduleConfigService = scheduleConfigService;
    }

    /**
     * {@code POST  /schedule-configs} : Create a new scheduleConfig.
     *
     * @param scheduleConfigDTO the scheduleConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduleConfigDTO, or with status {@code 400 (Bad Request)} if the scheduleConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedule-configs")
    public ResponseEntity<ScheduleConfigDTO> createScheduleConfig(@RequestBody ScheduleConfigDTO scheduleConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ScheduleConfig : {}", scheduleConfigDTO);
        if (scheduleConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleConfigDTO result = scheduleConfigService.save(scheduleConfigDTO);
        return ResponseEntity.created(new URI("/api/schedule-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schedule-configs} : Updates an existing scheduleConfig.
     *
     * @param scheduleConfigDTO the scheduleConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleConfigDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduleConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedule-configs")
    public ResponseEntity<ScheduleConfigDTO> updateScheduleConfig(@RequestBody ScheduleConfigDTO scheduleConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ScheduleConfig : {}", scheduleConfigDTO);
        if (scheduleConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleConfigDTO result = scheduleConfigService.save(scheduleConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduleConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schedule-configs} : get all the scheduleConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheduleConfigs in body.
     */
    @GetMapping("/schedule-configs")
    public List<ScheduleConfigDTO> getAllScheduleConfigs() {
        log.debug("REST request to get all ScheduleConfigs");
        return scheduleConfigService.findAll();
    }

    /**
     * {@code GET  /schedule-configs/:id} : get the "id" scheduleConfig.
     *
     * @param id the id of the scheduleConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduleConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedule-configs/{id}")
    public ResponseEntity<ScheduleConfigDTO> getScheduleConfig(@PathVariable Long id) {
        log.debug("REST request to get ScheduleConfig : {}", id);
        Optional<ScheduleConfigDTO> scheduleConfigDTO = scheduleConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleConfigDTO);
    }

    /**
     * {@code DELETE  /schedule-configs/:id} : delete the "id" scheduleConfig.
     *
     * @param id the id of the scheduleConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedule-configs/{id}")
    public ResponseEntity<Void> deleteScheduleConfig(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleConfig : {}", id);
        scheduleConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
