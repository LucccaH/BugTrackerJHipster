package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AlarmConfig;
import com.mycompany.myapp.repository.AlarmConfigRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AlarmConfig}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AlarmConfigResource {

    private final Logger log = LoggerFactory.getLogger(AlarmConfigResource.class);

    private static final String ENTITY_NAME = "alarmConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmConfigRepository alarmConfigRepository;

    public AlarmConfigResource(AlarmConfigRepository alarmConfigRepository) {
        this.alarmConfigRepository = alarmConfigRepository;
    }

    /**
     * {@code POST  /alarm-configs} : Create a new alarmConfig.
     *
     * @param alarmConfig the alarmConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmConfig, or with status {@code 400 (Bad Request)} if the alarmConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarm-configs")
    public ResponseEntity<AlarmConfig> createAlarmConfig(@RequestBody AlarmConfig alarmConfig) throws URISyntaxException {
        log.debug("REST request to save AlarmConfig : {}", alarmConfig);
        if (alarmConfig.getId() != null) {
            throw new BadRequestAlertException("A new alarmConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmConfig result = alarmConfigRepository.save(alarmConfig);
        return ResponseEntity.created(new URI("/api/alarm-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarm-configs} : Updates an existing alarmConfig.
     *
     * @param alarmConfig the alarmConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmConfig,
     * or with status {@code 400 (Bad Request)} if the alarmConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarm-configs")
    public ResponseEntity<AlarmConfig> updateAlarmConfig(@RequestBody AlarmConfig alarmConfig) throws URISyntaxException {
        log.debug("REST request to update AlarmConfig : {}", alarmConfig);
        if (alarmConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmConfig result = alarmConfigRepository.save(alarmConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alarmConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarm-configs} : get all the alarmConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmConfigs in body.
     */
    @GetMapping("/alarm-configs")
    public List<AlarmConfig> getAllAlarmConfigs() {
        log.debug("REST request to get all AlarmConfigs");
        return alarmConfigRepository.findAll();
    }

    /**
     * {@code GET  /alarm-configs/:id} : get the "id" alarmConfig.
     *
     * @param id the id of the alarmConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarm-configs/{id}")
    public ResponseEntity<AlarmConfig> getAlarmConfig(@PathVariable Long id) {
        log.debug("REST request to get AlarmConfig : {}", id);
        Optional<AlarmConfig> alarmConfig = alarmConfigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alarmConfig);
    }

    /**
     * {@code DELETE  /alarm-configs/:id} : delete the "id" alarmConfig.
     *
     * @param id the id of the alarmConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarm-configs/{id}")
    public ResponseEntity<Void> deleteAlarmConfig(@PathVariable Long id) {
        log.debug("REST request to delete AlarmConfig : {}", id);
        alarmConfigRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
